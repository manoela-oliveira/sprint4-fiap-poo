package br.com.sistemamotiva.exception;

public class TrechoInvalidoException extends RuntimeException {
    public TrechoInvalidoException(String mensagem) {
        super(mensagem);
    }
}