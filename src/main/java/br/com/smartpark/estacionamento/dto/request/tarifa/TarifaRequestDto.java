package br.com.smartpark.estacionamento.dto.request.tarifa;

import br.com.smartpark.estacionamento.model.Tarifa;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TarifaRequestDto(

        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 255, message = "A descrição deve ter no máixmo 255 caracteres")
        String descricao,

        @NotNull(message = "O valor da primeira hora deve ser informado")
        @PositiveOrZero(message = "O valor da primeira hora não pode ser negativo")
        @Digits(integer = 8, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
        BigDecimal valorPrimeiraHora,

        @NotNull(message = "O valor da hora adicional deve ser informado")
        @PositiveOrZero(message = "O calor da hora adicional não pode ser negativo")
        @Digits(integer = 8, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
        BigDecimal valorHoraAdicional,

        @Min(value = 0, message = "A tolerância não pode ser negativa")
        Short toleranciaMinutos
) {

    public Tarifa paraModel() {
        Tarifa tarifa = new Tarifa();
        tarifa.setDescricao(descricao);
        tarifa.setValorPrimeiraHora(valorPrimeiraHora);
        tarifa.setValorHoraAdicional(valorHoraAdicional);
        tarifa.setToleranciaMinutos(toleranciaMinutos);
        return tarifa;
    }
}
