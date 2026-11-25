package br.com.smartpark.estacionamento.controller.ticket;

import br.com.smartpark.estacionamento.dto.request.ticket.TicketRequestDto;
import br.com.smartpark.estacionamento.dto.response.ticket.TicketResponseDto;
import br.com.smartpark.estacionamento.service.ticket.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // Entrada do veículo: gera o ticket com o QR Code
    @PostMapping("/entrada")
    public ResponseEntity<TicketResponseDto> registrarEntrada(@RequestBody @Valid TicketRequestDto req) {
        TicketResponseDto ticket = ticketService.registrarEntrada(req);
        return ResponseEntity
                .created(URI.create("/tickets/" + ticket.qrCodeToken()))
                .body(ticket);
    }

    // Regra: consultar tickets em aberto
    @GetMapping("/abertos")
    public List<TicketResponseDto> listarAbertos() {
        return ticketService.listarAbertos();
    }

    @GetMapping("/{qrCodeToken}")
    public TicketResponseDto buscarPorQrCode(@PathVariable UUID qrCodeToken) {
        return ticketService.buscarPorQrCode(qrCodeToken);
    }

    // Quanto o cliente deve agora, antes de pagar
    @GetMapping("/{qrCodeToken}/valor")
    public BigDecimal consultarValor(@PathVariable UUID qrCodeToken) {
        return ticketService.consultarValor(qrCodeToken);
    }

    // Saída do veículo: só depois do pagamento
    @PatchMapping("/{qrCodeToken}/saida")
    public TicketResponseDto liberarSaida(@PathVariable UUID qrCodeToken) {
        return ticketService.liberarSaida(qrCodeToken);
    }
}