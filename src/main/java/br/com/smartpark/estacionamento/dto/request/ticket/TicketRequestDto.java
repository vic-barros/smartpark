package br.com.smartpark.estacionamento.dto.request.ticket;

import br.com.smartpark.estacionamento.model.Tarifa;
import br.com.smartpark.estacionamento.model.Ticket;
import br.com.smartpark.estacionamento.model.Vaga;
import br.com.smartpark.estacionamento.model.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TicketRequestDto(

        @NotBlank(message = "A placa do veículo é obrigatória ")
        String placa,

        @NotNull(message = "A vaga deve ser informada")
        Integer vagaId
) {

    public TicketRequestDto {
        if (placa != null) {
            placa = placa.trim().toUpperCase().replace("-", "");
        }
    }

    public Ticket paraModel(Veiculo veiculo, Vaga vaga, Tarifa tarifa) {
        Ticket ticket = new Ticket();
        ticket.setVeiculo(veiculo);
        ticket.setVaga(vaga);
        ticket.setTarifa(tarifa);
        return ticket;
    }
}
