package br.com.sistemamotiva.exception;

public class EquipeInvalidaException extends RuntimeException {
    public EquipeInvalidaException(String mensagem) {
        super(mensagem);
    }
}