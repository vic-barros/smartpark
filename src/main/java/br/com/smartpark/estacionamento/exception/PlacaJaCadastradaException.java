package br.com.smartpark.estacionamento.exception;

public class PlacaJaCadastradaException extends RegraNegocioException {
    public PlacaJaCadastradaException(String placa) {
        super("Placa" + " " + placa + "já cadastrada");
    }
}
