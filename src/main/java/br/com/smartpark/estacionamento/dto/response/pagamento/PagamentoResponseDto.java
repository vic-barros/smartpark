package br.com.smartpark.estacionamento.dto.response.pagamento;

import br.com.smartpark.estacionamento.model.Pagamento;
import br.com.smartpark.estacionamento.model.Ticket;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public record PagamentoResponseDto(
        Integer pagamentoId,
        Integer ticketId,
        String placa,
        LocalDateTime dataHoraEntrada,
        LocalDateTime dataHoraPagamento,
        Long permanenciaMinutos,
        BigDecimal valor
) {

    public static PagamentoResponseDto de(Pagamento pagamento) {
        Ticket ticket = pagamento.getTicket();

        return new PagamentoResponseDto(
                pagamento.getPagamentoId(),
                ticket.getTicketId(),
                ticket.getVeiculo().getPlaca(),
                ticket.getDataHoraEntrada(),
                pagamento.getDataHoraPagamento(),
                Duration.between(ticket.getDataHoraEntrada(), pagamento.getDataHoraPagamento()).toMinutes(),
                pagamento.getValor()
        );
    }
}