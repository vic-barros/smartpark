package br.com.smartpark.estacionamento.dto.response.ticket;

import br.com.smartpark.estacionamento.model.Tarifa;
import br.com.smartpark.estacionamento.model.Ticket;
import br.com.smartpark.estacionamento.model.Vaga;
import br.com.smartpark.estacionamento.model.Veiculo;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketResponseDto(
        Integer ticketId,
        Integer veiculoId,
        Integer vagaId,
        Integer tarifaId,
        UUID qrCodeToken,
        Boolean ativo,
        LocalDateTime dataHoraEntrada,
        LocalDateTime dataHoraSaida,
        LocalDateTime dataCadastro


) {

    public static TicketResponseDto de(Ticket ticket) {
        return new TicketResponseDto(
                ticket.getTicketId(),
                ticket.getVeiculo().getVeiculoId(),
                ticket.getVaga().getVagaId(),
                ticket.getTarifa().getTarifaId(),
                ticket.getQrCodeToken(),
                ticket.getAtivo(),
                ticket.getDataHoraEntrada(),
                ticket.getDataHoraSaida(),
                ticket.getDataCadastro()

        );
    }
}
