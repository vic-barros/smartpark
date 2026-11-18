package br.com.smartpark.estacionamento.service.vaga;

import br.com.smartpark.estacionamento.dto.request.vaga.VagaBloqueioRequestDto;
import br.com.smartpark.estacionamento.dto.request.vaga.VagaRequestDto;
import br.com.smartpark.estacionamento.dto.response.vaga.VagaResponseDto;
import br.com.smartpark.estacionamento.exception.RecursoNaoEncontradoException;
import br.com.smartpark.estacionamento.exception.RegraNegocioException;
import br.com.smartpark.estacionamento.model.TipoVaga;
import br.com.smartpark.estacionamento.model.Vaga;
import br.com.smartpark.estacionamento.repository.ticket.TicketRepository;
import br.com.smartpark.estacionamento.repository.tipovaga.TipoVagaRepository;
import br.com.smartpark.estacionamento.repository.vaga.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;
    private final TipoVagaRepository tipoVagaRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public VagaResponseDto cadastrar(VagaRequestDto req) {
        if (vagaRepository.existsByCodigo(req.codigo()))
            throw new RegraNegocioException("Já existe uma vaga com o código " + req.codigo());

        TipoVaga tipoVaga = buscarTipoVagaAtivo(req.tipoVagaId());
        Vaga salva = vagaRepository.save(req.paraModel(tipoVaga));
        return VagaResponseDto.de(salva);
    }

    @Transactional(readOnly = true)
    public VagaResponseDto buscar(Integer id) {
        return VagaResponseDto.de(buscarAtiva(id));
    }

    @Transactional(readOnly = true)
    public List<VagaResponseDto> listar() {
        return vagaRepository.findByAtivoTrueOrderByCodigoAsc()
                .stream()
                .map(VagaResponseDto::de)
                .toList();
    }

    // Regra: consultar vagas livres
    @Transactional(readOnly = true)
    public List<VagaResponseDto> listarLivres() {
        return vagaRepository.buscarVagasLivres()
                .stream()
                .map(VagaResponseDto::de)
                .toList();
    }

    // Regra: consultar vagas ocupadas
    @Transactional(readOnly = true)
    public List<VagaResponseDto> listarOcupadas() {
        return vagaRepository.buscarVagasOcupadas()
                .stream()
                .map(VagaResponseDto::de)
                .toList();
    }

    @Transactional
    public VagaResponseDto atualizar(Integer id, VagaRequestDto req) {
        Vaga vaga = buscarAtiva(id);

        // Só verifica duplicidade se o código mudou
        boolean codigoMudou = !vaga.getCodigo().equals(req.codigo());
        if (codigoMudou && vagaRepository.existsByCodigo(req.codigo()))
            throw new RegraNegocioException("Já existe uma vaga com o código " + req.codigo());

        vaga.setCodigo(req.codigo());
        vaga.setTipoVaga(buscarTipoVagaAtivo(req.tipoVagaId()));
        return VagaResponseDto.de(vaga);
    }

    @Transactional
    public VagaResponseDto bloquear(Integer id, VagaBloqueioRequestDto req) {
        Vaga vaga = buscarAtiva(id);

        if (vaga.getBloqueada())
            throw new RegraNegocioException("A vaga " + vaga.getCodigo() + " já está bloqueada");
        if (estaOcupada(vaga))
            throw new RegraNegocioException("A vaga " + vaga.getCodigo() + " está ocupada e não pode ser bloqueada");

        vaga.setBloqueada(true);
        vaga.setMotivoBloqueio(req.motivoBloqueio());
        return VagaResponseDto.de(vaga);
    }

    @Transactional
    public VagaResponseDto desbloquear(Integer id) {
        Vaga vaga = buscarAtiva(id);

        if (!vaga.getBloqueada())
            throw new RegraNegocioException("A vaga " + vaga.getCodigo() + " não está bloqueada");

        vaga.setBloqueada(false);
        vaga.setMotivoBloqueio(null);
        return VagaResponseDto.de(vaga);
    }

    @Transactional
    public void inativar(Integer id) {
        Vaga vaga = buscarAtiva(id);

        if (estaOcupada(vaga))
            throw new RegraNegocioException("A vaga " + vaga.getCodigo() + " está ocupada e não pode ser inativada");

        vaga.setAtivo(false);
    }

    private boolean estaOcupada(Vaga vaga) {
        return ticketRepository.existsByVagaVagaIdAndDataHoraSaidaIsNull(vaga.getVagaId());
    }

    private Vaga buscarAtiva(Integer id) {
        return vagaRepository.findById(id)
                .filter(Vaga::getAtivo)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Vaga " + id + " não encontrada."));
    }

    private TipoVaga buscarTipoVagaAtivo(Integer tipoVagaId) {
        return tipoVagaRepository.findById(tipoVagaId)
                .filter(TipoVaga::getAtivo)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tipo de vaga " + tipoVagaId + " não encontrado."));
    }
}