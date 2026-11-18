package br.com.smartpark.estacionamento.service.ticket;

import br.com.smartpark.estacionamento.model.Tarifa;
import br.com.smartpark.estacionamento.model.Ticket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CalculadoraTarifa {

    // Regra: até a tolerância é grátis; depois, primeira hora + cada hora adicional iniciada
    public BigDecimal calcular(Ticket ticket, LocalDateTime momento) {
        Tarifa tarifa = ticket.getTarifa();
        long minutos = Duration.between(ticket.getDataHoraEntrada(), momento).toMinutes();

        if (minutos <= tarifa.getToleranciaMinutos()) {
            return BigDecimal.ZERO;
        }
        if (minutos <= 60) {
            return tarifa.getValorPrimeiraHora();
        }
        long horasAdicionais = (minutos - 60 + 59) / 60;   // arredonda para cima
        return tarifa.getValorPrimeiraHora()
                .add(tarifa.getValorHoraAdicional().multiply(BigDecimal.valueOf(horasAdicionais)));
    }
}