package br.com.smartpark.estacionamento.repository.veiculo;

import br.com.smartpark.estacionamento.enums.TipoVeiculo;

public interface TotalPorTipo {
    TipoVeiculo getTipo();

    Long getTotal();
}
