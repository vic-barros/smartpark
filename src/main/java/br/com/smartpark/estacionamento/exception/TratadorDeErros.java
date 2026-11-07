package br.com.smartpark.estacionamento.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<StandardError> naoEncontrado(
            RecursoNaoEncontradoException ex,
            HttpServletRequest request) {
        return resposta(HttpStatus.NOT_FOUND,
                "Não encontrado", ex.getMessage(), request);
    }

    @ExceptionHandler(PlacaJaCadastradaException.class)
    public ResponseEntity<StandardError> placaDuplicada(
            PlacaJaCadastradaException ex,
            HttpServletRequest request) {
        return resposta(HttpStatus.CONFLICT,
                "Placa já cadastrada", ex.getMessage(), request);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<StandardError> regraNegocio(
            RegraNegocioException ex,
            HttpServletRequest request) {
        return resposta(HttpStatus.UNPROCESSABLE_ENTITY,
                "Regra de negócio", ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validacao(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        ValidationError err = new ValidationError(
                HttpStatus.BAD_REQUEST.value(), "Erro de validação",
                "Um ou mais campos estão inválidos.",
                request.getRequestURI());

        for (FieldError e : ex.getBindingResult().getFieldErrors()) {
            err.addError(e.getField(), e.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> jsonInvalido(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {
        return resposta(HttpStatus.BAD_REQUEST, "Erro de leitura JSON",
                "Verifique o formato dos dados enviados. "
                + "tipo aceita CARRO, MOTO ou UTILITARIO.", request);
    }

    private ResponseEntity<StandardError> resposta(
            HttpStatus status, String error,
            String message, HttpServletRequest request) {
        StandardError err = new StandardError(LocalDateTime.now(),
                status.value(), error, message,
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
