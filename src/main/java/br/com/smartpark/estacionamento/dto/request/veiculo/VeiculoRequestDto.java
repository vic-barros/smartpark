package br.com.smartpark.estacionamento.dto.request.veiculo;

import br.com.smartpark.estacionamento.enums.TipoVeiculo;
import br.com.smartpark.estacionamento.model.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VeiculoRequestDto(

        @NotBlank(message = "A placa é obrigatória")
        @Pattern(regexp = "[A-Z]{3}[0-9][A-Z0-9][0-9]{2}", message = "Placa inválida (ABC1234 ou ABC1D23)")
        @Size(max = 10)
        String placa,

        @NotBlank(message = "O modelo é obrigatório")
        @Size(max = 50)
        String modelo,

        @NotBlank(message = "A cor é obrigatória")
        @Size(max = 30)
        String cor,

        @NotNull(message = "O tipo do veículo é obrigatório")
        TipoVeiculo tipoVeiculo
) {

    // Construtor compacto apenas para normalizar o valor recebido na placa
    public VeiculoRequestDto {
        if (placa != null) {
            placa = placa.trim().toUpperCase().replace("-", "");
        }
    }

    public Veiculo paraModel() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(placa);
        veiculo.setModelo(modelo);
        veiculo.setCor(cor);
        veiculo.setTipoVeiculo(tipoVeiculo);
        return veiculo;
    }
}
