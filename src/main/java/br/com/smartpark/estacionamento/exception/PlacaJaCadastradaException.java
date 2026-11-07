package br.com.smartpark.estacionamento.exception;

public class PlacaJaCadastradaException extends RegraNegocioException {
    public PlacaJaCadastradaException(String message, String placa) {
        super("Placa" + placa + message);
    }
}
