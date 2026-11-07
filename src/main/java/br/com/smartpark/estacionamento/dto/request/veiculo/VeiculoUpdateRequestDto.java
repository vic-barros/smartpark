package br.com.smartpark.estacionamento.dto.request.veiculo;

import br.com.smartpark.estacionamento.enums.TipoVeiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VeiculoUpdateRequestDto(

        @NotBlank(message = "O modelo é obrigatório")
        @Size(max = 50)
        String modelo,

        @NotBlank(message = "A cor é obrigatória")
        @Size(max = 30)
        String cor,

        @NotNull(message = "O tipo é obrigatório")
        TipoVeiculo tipoVeiculo
) {
}
