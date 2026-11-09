package br.com.smartpark.estacionamento.dto.response.tarifa;

import br.com.smartpark.estacionamento.model.Tarifa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TarifaResponseDto(
        Integer tarifaId,
        String descricao,
        BigDecimal valorPrimeiraHora,
        BigDecimal valorHoraAdicional,
        Short toleranciaMinutos,
        boolean ativo,
        LocalDateTime dataCadastro
) {

    public static TarifaResponseDto de(Tarifa tarifa) {
        return new TarifaResponseDto(
                tarifa.getTarifaId(),
                tarifa.getDescricao(),
                tarifa.getValorPrimeiraHora(),
                tarifa.getValorHoraAdicional(),
                tarifa.getToleranciaMinutos(),
                tarifa.getAtivo(),
                tarifa.getDataCadastro()
        );
    }
}
