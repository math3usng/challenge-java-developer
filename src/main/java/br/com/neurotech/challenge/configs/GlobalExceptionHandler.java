package br.com.neurotech.challenge.configs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Um manipulador global de exceções para a aplicação que intercepta exceções
 * e formata respostas apropriadas para o cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Lida com exceções lançadas quando os argumentos de um método falham na validação.
     *
     * @param ex a exceção contendo os erros de validação
     * @return uma entidade de resposta com um mapa de campos com erros e suas respectivas mensagens,
     * junto com um código de status BAD_REQUEST (400)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
