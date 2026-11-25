package br.com.smartpark.estacionamento.controller.vaga;

import br.com.smartpark.estacionamento.dto.request.vaga.VagaBloqueioRequestDto;
import br.com.smartpark.estacionamento.dto.request.vaga.VagaRequestDto;
import br.com.smartpark.estacionamento.dto.response.vaga.VagaResponseDto;
import br.com.smartpark.estacionamento.service.vaga.VagaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vagas")
@RequiredArgsConstructor
public class VagaController {

    private final VagaService vagaService;

    @GetMapping
    public List<VagaResponseDto> listar() {
        return vagaService.listar();
    }

    // Regra: consultar vagas livres
    @GetMapping("/livres")
    public List<VagaResponseDto> listarLivres() {
        return vagaService.listarLivres();
    }

    // Regra: consultar vagas ocupadas
    @GetMapping("/ocupadas")
    public List<VagaResponseDto> listarOcupadas() {
        return vagaService.listarOcupadas();
    }

    @GetMapping("/{id}")
    public VagaResponseDto buscar(@PathVariable Integer id) {
        return vagaService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<VagaResponseDto> cadastrar(@RequestBody @Valid VagaRequestDto req) {
        VagaResponseDto criada = vagaService.cadastrar(req);
        return ResponseEntity
                .created(URI.create("/vagas/" + criada.vagaId()))
                .body(criada);
    }

    @PutMapping("/{id}")
    public VagaResponseDto atualizar(@PathVariable Integer id,
                                     @RequestBody @Valid VagaRequestDto req) {
        return vagaService.atualizar(id, req);
    }

    @PatchMapping("/{id}/bloqueio")
    public VagaResponseDto bloquear(@PathVariable Integer id,
                                    @RequestBody @Valid VagaBloqueioRequestDto req) {
        return vagaService.bloquear(id, req);
    }

    @PatchMapping("/{id}/desbloqueio")
    public VagaResponseDto desbloquear(@PathVariable Integer id) {
        return vagaService.desbloquear(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Integer id) {
        vagaService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}