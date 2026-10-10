package br.com.sistemamotiva.dao;

import br.com.sistemamotiva.db.ConexaoBanco;
import br.com.sistemamotiva.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrechoRodoviaDAO {

    private static final String SQL_INSERT = 
        "INSERT INTO trechos (codigo, km_inicial, km_final, nivel_vegetacao, tipo, regiao_umida, quantidade_faixas, is_pavimentada) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM trechos WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM trechos ORDER BY id";
    private static final String SQL_UPDATE = 
        "UPDATE trechos SET nivel_vegetacao = ?, regiao_umida = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM trechos WHERE id = ?";

    public TrechoRodoviaDAO() {}

    public void inserir(TrechoRodovia trecho) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoBanco.getConexao();
            pstmt = conn.prepareStatement(SQL_INSERT, new String[]{"ID"});
            pstmt.setString(1, trecho.getIdentificador().getCodigoIdentificacao());
            pstmt.setDouble(2, trecho.getIdentificador().getQuilometroInicial());
            pstmt.setDouble(3, trecho.getIdentificador().getQuilometroFinal());
            pstmt.setDouble(4, trecho.getNivelVegetacaoCm());
            pstmt.setString(5, trecho.getTipo());
            pstmt.setInt(6, trecho.isRegiaoUmida() ? 1 : 0);

            if (trecho instanceof Autoestrada auto) {
                pstmt.setInt(7, auto.getQuantidadeFaixas());
                pstmt.setInt(8, 1);
            } else if (trecho instanceof EstradaVicinal vic) {
                pstmt.setInt(7, 1);
                pstmt.setInt(8, vic.isPavimentada() ? 1 : 0);
            } else {
                pstmt.setInt(7, 1);
                pstmt.setInt(8, 1);
            }

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    trecho.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir trecho: " + e.getMessage(), e);
        } finally {
            fecharRecursos(rs, pstmt, conn);
        }
    }

    public TrechoRodovia buscarPorId(Long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoBanco.getConexao();
            pstmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return extrairTrecho(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trecho: " + e.getMessage(), e);
        } finally {
            fecharRecursos(rs, pstmt, conn);
        }
    }

    public List<TrechoRodovia> listarTodas() {
        List<TrechoRodovia> lista = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_SELECT_ALL);
            while (rs.next()) {
                lista.add(extrairTrecho(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar trechos: " + e.getMessage(), e);
        } finally {
            fecharRecursos(rs, stmt, conn);
        }
    }

    public void atualizar(TrechoRodovia trecho) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoBanco.getConexao();
            pstmt = conn.prepareStatement(SQL_UPDATE);
            pstmt.setDouble(1, trecho.getNivelVegetacaoCm());
            pstmt.setInt(2, trecho.isRegiaoUmida() ? 1 : 0);
            pstmt.setLong(3, trecho.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar trecho: " + e.getMessage(), e);
        } finally {
            fecharRecursos(null, pstmt, conn);
        }
    }

    public void deletar(Long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoBanco.getConexao();
            pstmt = conn.prepareStatement(SQL_DELETE);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar trecho: " + e.getMessage(), e);
        } finally {
            fecharRecursos(null, pstmt, conn);
        }
    }

    private TrechoRodovia extrairTrecho(ResultSet rs) throws SQLException {
        IdentificacaoTrecho iden = new IdentificacaoTrecho(
            rs.getString("codigo"),
            rs.getDouble("km_inicial"),
            rs.getDouble("km_final")
        );
        double nivel = rs.getDouble("nivel_vegetacao");
        String tipo = rs.getString("tipo");
        boolean umida = rs.getInt("regiao_umida") == 1;

        TrechoRodovia trecho;
        if ("AUTOESTRADA".equalsIgnoreCase(tipo)) {
            int faixas = rs.getInt("quantidade_faixas");
            trecho = new Autoestrada(iden, nivel, faixas);
        } else {
            boolean pav = rs.getInt("is_pavimentada") == 1;
            trecho = new EstradaVicinal(iden, nivel, pav);
        }

        trecho.setId(rs.getLong("id"));
        if (umida) {
            trecho.marcarComoRegiaoUmida();
        }
        return trecho;
    }

    private void fecharRecursos(ResultSet rs, Statement stmt, Connection conn) {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (stmt != null) stmt.close(); } catch (SQLException ignored) {}
        ConexaoBanco.fechar(conn);
    }
}