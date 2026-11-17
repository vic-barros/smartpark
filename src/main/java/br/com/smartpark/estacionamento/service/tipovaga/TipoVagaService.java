package br.com.smartpark.estacionamento.service.tipovaga;

import br.com.smartpark.estacionamento.dto.request.tipovaga.TipoVagaRequestDto;
import br.com.smartpark.estacionamento.dto.response.tipovaga.TipoVagaResponseDto;
import br.com.smartpark.estacionamento.exception.RecursoNaoEncontradoException;
import br.com.smartpark.estacionamento.exception.RegraNegocioException;
import br.com.smartpark.estacionamento.model.TipoVaga;
import br.com.smartpark.estacionamento.repository.tipovaga.TipoVagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TipoVagaService {

    private final TipoVagaRepository tipoVagaRepository;

    @Transactional
    public TipoVagaResponseDto cadastrar(TipoVagaRequestDto req) {
        if (tipoVagaRepository.existsByNomeIgnoreCase(req.nome()))
            throw new RegraNegocioException("Já existe um tipo de vaga com o nome " + req.nome());

        TipoVaga salvo = tipoVagaRepository.save(req.paraModel());
        return TipoVagaResponseDto.de(salvo);
    }

    @Transactional(readOnly = true)
    public TipoVagaResponseDto buscar(Integer id) {
        return TipoVagaResponseDto.de(buscarAtivo(id));
    }

    @Transactional
    public TipoVagaResponseDto atualizar(Integer id, TipoVagaRequestDto req) {
        TipoVaga tipoVaga = buscarAtivo(id);

        // Só verifica duplicidade se o nome mudou
        boolean nomeMudou = !tipoVaga.getNome().equalsIgnoreCase(req.nome());
        if (nomeMudou && tipoVagaRepository.existsByNomeIgnoreCase(req.nome()))
            throw new RegraNegocioException("Já existe um tipo de vaga com o nome " + req.nome());

        tipoVaga.setNome(req.nome());
        tipoVaga.setDescricao(req.descricao());
        tipoVaga.setExigeCredencial(req.exigeCredencial());
        return TipoVagaResponseDto.de(tipoVaga);
    }

    @Transactional
    public void inativar(Integer id) {
        TipoVaga tipoVaga = buscarAtivo(id);
        tipoVaga.setAtivo(false);
    }


    private TipoVaga buscarAtivo(Integer id) {
        return tipoVagaRepository.findById(id)
                .filter(TipoVaga::getAtivo)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tipo de vaga " + id + " não encontrado."));
    }

}
