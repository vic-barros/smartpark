package br.com.smartpark.estacionamento.controller.veiculo;

import br.com.smartpark.estacionamento.dto.request.veiculo.VeiculoRequestDto;
import br.com.smartpark.estacionamento.dto.request.veiculo.VeiculoUpdateRequestDto;
import br.com.smartpark.estacionamento.dto.response.veiculo.VeiculoResponseDto;
import br.com.smartpark.estacionamento.service.veiculo.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @GetMapping
    public Page<VeiculoResponseDto> listar(
            @ParameterObject
            @PageableDefault(size = 10, sort = "modelo") Pageable pageable) {
        return veiculoService.listar(pageable);
    }

    @GetMapping("/{id}")
    public VeiculoResponseDto buscar(@PathVariable Integer id) {
        return veiculoService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<VeiculoResponseDto> cadastrar(@RequestBody @Valid VeiculoRequestDto req) {
        VeiculoResponseDto criado = veiculoService.cadastrar(req);
        return ResponseEntity
                .created(URI.create("/veiculos/" + criado.veiculoId()))
                .body(criado);
    }

    @PutMapping("/{id}")
    public VeiculoResponseDto atualizar(@PathVariable Integer id,
                                        @RequestBody @Valid VeiculoUpdateRequestDto req) {
        return veiculoService.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Integer id) {
        veiculoService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}