package br.com.sistemamotiva.exception;

public class CredenciaisInvalidasException extends RuntimeException {
    public CredenciaisInvalidasException(String message) {
        super(message);
    }

    public CredenciaisInvalidasException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getDicaCorrecao() {
        return """
        ------------------------------------------------------------
        ERRO DE AUTENTICAÇÃO NO ORACLE (BANCO DE DADOS)
        Verifique usuário e senha no ConexaoBanco.java ou nas variáveis de ambiente DB_USER e DB_PASSWORD.
        """;
    }
}