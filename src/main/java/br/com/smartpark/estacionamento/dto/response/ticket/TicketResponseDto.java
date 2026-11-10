package br.com.smartpark.estacionamento.dto.response.ticket;

import br.com.smartpark.estacionamento.model.Ticket;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketResponseDto(
        Integer ticketId,
        UUID qrCodeToken,
        String placa,
        String vaga,
        String tarifa,
        LocalDateTime dataHoraEntrada,
        LocalDateTime dataHoraSaida


) {

    public static TicketResponseDto de(Ticket ticket) {
        return new TicketResponseDto(
                ticket.getTicketId(),
                ticket.getQrCodeToken(),
                ticket.getVeiculo().getPlaca(),
                ticket.getVaga().getCodigo(),
                ticket.getTarifa().getDescricao(),
                ticket.getDataHoraEntrada(),
                ticket.getDataHoraSaida()
        );
    }
}
