package br.com.smartpark.estacionamento.dto.response.veiculo;

import br.com.smartpark.estacionamento.enums.TipoVeiculo;
import br.com.smartpark.estacionamento.model.Veiculo;

import java.time.LocalDateTime;

public record VeiculoResponseDto(
        Integer veiculoId,
        String placa,
        String modelo,
        String cor,
        TipoVeiculo tipoVeiculo,
        LocalDateTime dataCadastro
) {

    public static VeiculoResponseDto de(Veiculo veiculo) {
        return new VeiculoResponseDto(
                veiculo.getVeiculoId(),
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getCor(),
                veiculo.getTipoVeiculo(),
                veiculo.getDataCadastro()
        );
    }
}
