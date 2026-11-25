package br.com.smartpark.estacionamento.service.veiculo;

import br.com.smartpark.estacionamento.dto.request.veiculo.VeiculoRequestDto;
import br.com.smartpark.estacionamento.dto.request.veiculo.VeiculoUpdateRequestDto;
import br.com.smartpark.estacionamento.dto.response.veiculo.VeiculoResponseDto;
import br.com.smartpark.estacionamento.exception.PlacaJaCadastradaException;
import br.com.smartpark.estacionamento.exception.RecursoNaoEncontradoException;
import br.com.smartpark.estacionamento.model.Veiculo;
import br.com.smartpark.estacionamento.repository.veiculo.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // Gera o construtor com argumentos obrigatórios
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    @Transactional
    public VeiculoResponseDto cadastrar(VeiculoRequestDto req) {
        if (veiculoRepository.existsByPlaca(req.placa()))
            throw new PlacaJaCadastradaException(req.placa());

        Veiculo salvo = veiculoRepository.save(req.paraModel());
        return VeiculoResponseDto.de(salvo);
    }

    @Transactional(readOnly = true)
    public VeiculoResponseDto buscar(Integer id) {
        return VeiculoResponseDto.de(buscarAtivo(id));
    }

    @Transactional(readOnly = true)
    public Page<VeiculoResponseDto> listar(Pageable pageable) {
        return veiculoRepository.findByAtivoTrue(pageable).map(VeiculoResponseDto::de);
    }

    @Transactional
    public VeiculoResponseDto atualizar(Integer id, VeiculoUpdateRequestDto req) {
        Veiculo veiculo = buscarAtivo(id);
        veiculo.setModelo(req.modelo());
        veiculo.setCor(req.cor());
        veiculo.setTipoVeiculo(req.tipoVeiculo());
        return VeiculoResponseDto.de(veiculo);
    }

    @Transactional
    public void inativar(Integer id) {
        Veiculo veiculo = buscarAtivo(id);
        veiculo.setAtivo(false);
    }

    private Veiculo buscarAtivo(Integer id) {
        return veiculoRepository.findByVeiculoIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Veículo " + id + " não encontrado."));
    }
}