package br.com.smartpark.estacionamento.controller.tarifa;

import br.com.smartpark.estacionamento.dto.request.tarifa.TarifaRequestDto;
import br.com.smartpark.estacionamento.dto.response.tarifa.TarifaResponseDto;
import br.com.smartpark.estacionamento.service.tarifa.TarifaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tarifas")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService tarifaService;

    // Histórico completo, da mais recente para a mais antiga
    @GetMapping
    public List<TarifaResponseDto> listar() {
        return tarifaService.listar();
    }

    // A tarifa usada nas novas entradas
    @GetMapping("/vigente")
    public TarifaResponseDto buscarVigente() {
        return tarifaService.buscarVigente();
    }

    @GetMapping("/{id}")
    public TarifaResponseDto buscar(@PathVariable Integer id) {
        return tarifaService.buscar(id);
    }

    // Cadastrar uma nova tarifa substitui a vigente
    @PostMapping
    public ResponseEntity<TarifaResponseDto> cadastrar(@RequestBody @Valid TarifaRequestDto req) {
        TarifaResponseDto criada = tarifaService.cadastrar(req);
        return ResponseEntity
                .created(URI.create("/tarifas/" + criada.tarifaId()))
                .body(criada);
    }
}