package br.com.smartpark.estacionamento.repository;

import br.com.smartpark.estacionamento.enums.TipoVeiculo;

public interface TotalPorTipo {
    TipoVeiculo getTipo();

    Long getTotal();
}
