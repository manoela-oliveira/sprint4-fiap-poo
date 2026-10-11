package br.com.motiva.sistemamotiva.exception;

public class EquipeInvalidaException extends RuntimeException {
    public EquipeInvalidaException(String mensagem) {
        super(mensagem);
    }
}