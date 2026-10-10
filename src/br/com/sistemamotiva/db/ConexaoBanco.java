package br.com.sistemamotiva.db;

import br.com.sistemamotiva.exception.CredenciaisInvalidasException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    private static final String HOST = "oracle.fiap.com.br";
    private static final String PORT = "1521";
    private static final String SID = "ORCL";

    private static final String USER = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "SUA_USUARIO";
    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "SUA_SENHA";

    public static Connection getConexao() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@" + HOST + ":" + PORT + ":" + SID;
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver Oracle não encontrado no classpath: " + e.getMessage(), e);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1017) {
                throw new CredenciaisInvalidasException(
                    "Credenciais inválidas para o usuário '" + USER + "'.", e
                );
            }
            throw new RuntimeException("Erro ao conectar ao Oracle: " + e.getMessage(), e);
        }
    }

    public static void fechar(Connection conexao) {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}