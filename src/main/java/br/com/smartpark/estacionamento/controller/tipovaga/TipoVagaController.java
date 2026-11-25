package br.com.smartpark.estacionamento.controller.tipovaga;

import br.com.smartpark.estacionamento.dto.request.tipovaga.TipoVagaRequestDto;
import br.com.smartpark.estacionamento.dto.response.tipovaga.TipoVagaResponseDto;
import br.com.smartpark.estacionamento.service.tipovaga.TipoVagaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tipos-vaga")
@RequiredArgsConstructor
public class TipoVagaController {

    private final TipoVagaService tipoVagaService;

    @GetMapping
    public List<TipoVagaResponseDto> listar() {
        return tipoVagaService.listar();
    }

    @GetMapping("/{id}")
    public TipoVagaResponseDto buscar(@PathVariable Integer id) {
        return tipoVagaService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<TipoVagaResponseDto> cadastrar(@RequestBody @Valid TipoVagaRequestDto req) {
        TipoVagaResponseDto criado = tipoVagaService.cadastrar(req);
        return ResponseEntity
                .created(URI.create("/tipos-vaga/" + criado.tipoVagaId()))
                .body(criado);
    }

    @PutMapping("/{id}")
    public TipoVagaResponseDto atualizar(@PathVariable Integer id,
                                         @RequestBody @Valid TipoVagaRequestDto req) {
        return tipoVagaService.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Integer id) {
        tipoVagaService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}