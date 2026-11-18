package br.com.smartpark.estacionamento.service;

import br.com.smartpark.estacionamento.dto.request.tarifa.TarifaRequestDto;
import br.com.smartpark.estacionamento.dto.response.tarifa.TarifaResponseDto;
import br.com.smartpark.estacionamento.exception.RecursoNaoEncontradoException;
import br.com.smartpark.estacionamento.exception.RegraNegocioException;
import br.com.smartpark.estacionamento.model.Tarifa;
import br.com.smartpark.estacionamento.repository.tarifa.TarifaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifaRepository tarifaRepository;

    // Regra: só pode existir uma tarifa ativa por vez.
    // A nova tarifa substitui a vigente, que é inativada (mas continua no banco,
    // porque os tickets antigos apontam para ela).
    @Transactional
    public TarifaResponseDto cadastrar(TarifaRequestDto req) {
        tarifaRepository.findByAtivoTrue()
                .forEach(tarifa -> tarifa.setAtivo(false));

        Tarifa salva = tarifaRepository.save(req.paraModel());
        return TarifaResponseDto.de(salva);
    }

    // Busca qualquer tarifa, ativa ou não: as antigas fazem parte do histórico
    @Transactional(readOnly = true)
    public TarifaResponseDto buscar(Integer id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tarifa " + id + " não encontrada."));
        return TarifaResponseDto.de(tarifa);
    }

    // Histórico completo, da mais recente para a mais antiga
    @Transactional(readOnly = true)
    public List<TarifaResponseDto> listar() {
        return tarifaRepository.findAll(Sort.by(Sort.Direction.DESC, "dataCadastro"))
                .stream()
                .map(TarifaResponseDto::de)
                .toList();
    }

    // A tarifa usada nas novas entradas
    @Transactional(readOnly = true)
    public TarifaResponseDto buscarVigente() {
        return TarifaResponseDto.de(buscarTarifaVigente());
    }

    private Tarifa buscarTarifaVigente() {
        return tarifaRepository.findFirstByAtivoTrueOrderByDataCadastroDesc()
                .orElseThrow(() -> new RegraNegocioException("Nenhuma tarifa ativa cadastrada"));
    }
}