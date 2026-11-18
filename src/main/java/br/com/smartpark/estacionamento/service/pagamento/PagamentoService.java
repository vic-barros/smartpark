package br.com.smartpark.estacionamento.service.pagamento;

import br.com.smartpark.estacionamento.dto.request.pagamento.PagamentoRequestDto;
import br.com.smartpark.estacionamento.dto.response.pagamento.PagamentoResponseDto;
import br.com.smartpark.estacionamento.exception.RecursoNaoEncontradoException;
import br.com.smartpark.estacionamento.exception.RegraNegocioException;
import br.com.smartpark.estacionamento.model.Pagamento;
import br.com.smartpark.estacionamento.model.Ticket;
import br.com.smartpark.estacionamento.repository.pagamento.PagamentoRepository;
import br.com.smartpark.estacionamento.repository.ticket.TicketRepository;
import br.com.smartpark.estacionamento.service.ticket.CalculadoraTarifa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final TicketRepository ticketRepository;
    private final CalculadoraTarifa calculadoraTarifa;

    @Transactional
    public PagamentoResponseDto pagar(PagamentoRequestDto req) {
        Ticket ticket = buscarTicket(req.qrCodeToken());

        // Regra: um ticket pago não pode ser pago novamente
        if (pagamentoRepository.existsByTicketTicketId(ticket.getTicketId()))
            throw new RegraNegocioException("Este ticket já foi pago");

        LocalDateTime agora = LocalDateTime.now();

        Pagamento pagamento = new Pagamento();
        pagamento.setTicket(ticket);
        pagamento.setDataHoraPagamento(agora);
        pagamento.setValor(calculadoraTarifa.calcular(ticket, agora));   // o valor nunca vem do cliente

        return PagamentoResponseDto.de(pagamentoRepository.save(pagamento));
    }

    // Comprovante: o pagamento de um ticket, a partir do QR Code
    @Transactional(readOnly = true)
    public PagamentoResponseDto buscarPorQrCode(UUID qrCodeToken) {
        Ticket ticket = buscarTicket(qrCodeToken);

        Pagamento pagamento = pagamentoRepository.findByTicketTicketId(ticket.getTicketId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Nenhum pagamento registrado para este ticket"));

        return PagamentoResponseDto.de(pagamento);
    }

    private Ticket buscarTicket(UUID qrCodeToken) {
        return ticketRepository.findByQrCodeToken(qrCodeToken)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Ticket não encontrado para o QR Code informado"));
    }
}