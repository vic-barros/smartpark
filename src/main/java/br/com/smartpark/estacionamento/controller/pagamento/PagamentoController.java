package br.com.smartpark.estacionamento.controller.pagamento;

import br.com.smartpark.estacionamento.dto.request.pagamento.PagamentoRequestDto;
import br.com.smartpark.estacionamento.dto.response.pagamento.PagamentoResponseDto;
import br.com.smartpark.estacionamento.service.pagamento.PagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    // Paga o ticket do QR Code informado; o valor é calculado pelo sistema
    @PostMapping
    public ResponseEntity<PagamentoResponseDto> pagar(@RequestBody @Valid PagamentoRequestDto req) {
        PagamentoResponseDto pagamento = pagamentoService.pagar(req);
        return ResponseEntity
                .created(URI.create("/pagamentos/" + req.qrCodeToken()))
                .body(pagamento);
    }

    // Comprovante do pagamento de um ticket
    @GetMapping("/{qrCodeToken}")
    public PagamentoResponseDto buscarPorQrCode(@PathVariable UUID qrCodeToken) {
        return pagamentoService.buscarPorQrCode(qrCodeToken);
    }
}