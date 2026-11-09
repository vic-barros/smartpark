package br.com.smartpark.estacionamento.dto.request.ticket;

import br.com.smartpark.estacionamento.model.Tarifa;
import br.com.smartpark.estacionamento.model.Ticket;
import br.com.smartpark.estacionamento.model.Vaga;
import br.com.smartpark.estacionamento.model.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TicketRequestDto(
        @NotNull(message = "O veículo deve ser informado")
        Veiculo veiculoId,

        @NotNull(message = "A vaga deve ser informada")
        Vaga vagaId,

        @NotNull(message = "A tarifa deve ser informada")
        Tarifa tarifaId


) {

    public Ticket paraModel() {
        Ticket ticket = new Ticket();
        ticket.setVeiculo(veiculoId);
        ticket.setVaga(vagaId);
        ticket.setTarifa(tarifaId);
        return ticket;
    }
}
