package br.com.sistemamotiva.dao;

import br.com.sistemamotiva.db.ConexaoBanco;
import br.com.sistemamotiva.model.EquipeManutencao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeManutencaoDAO {

    private static final String SQL_INSERT = "INSERT INTO equipes (identificador, quantidade_membros) VALUES (?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM equipes WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM equipes ORDER BY id";
    private static final String SQL_UPDATE = "UPDATE equipes SET identificador = ?, quantidade_membros = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM equipes WHERE id = ?";

    public EquipeManutencaoDAO() {}

    public void inserir(EquipeManutencao equipe) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoBanco.getConexao();
            pstmt = conn.prepareStatement(SQL_INSERT, new String[]{"ID"});
            pstmt.setString(1, equipe.getIdentificadorEquipe());
            pstmt.setInt(2, equipe.getQuantidadeMembros());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    equipe.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir equipe: " + e.getMessage(), e);
        } finally {
            fecharRecursos(rs, pstmt, conn);
        }
    }

    public EquipeManutencao buscarPorId(Long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoBanco.getConexao();
            pstmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new EquipeManutencao(
                    rs.getLong("id"),
                    rs.getString("identificador"),
                    rs.getInt("quantidade_membros")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar equipe: " + e.getMessage(), e);
        } finally {
            fecharRecursos(rs, pstmt, conn);
        }
    }

    public List<EquipeManutencao> listarTodas() {
        List<EquipeManutencao> lista = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_SELECT_ALL);
            while (rs.next()) {
                lista.add(new EquipeManutencao(
                    rs.getLong("id"),
                    rs.getString("identificador"),
                    rs.getInt("quantidade_membros")
                ));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar equipes: " + e.getMessage(), e);
        } finally {
            fecharRecursos(rs, stmt, conn);
        }
    }

    public void atualizar(EquipeManutencao equipe) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoBanco.getConexao();
            pstmt = conn.prepareStatement(SQL_UPDATE);
            pstmt.setString(1, equipe.getIdentificadorEquipe());
            pstmt.setInt(2, equipe.getQuantidadeMembros());
            pstmt.setLong(3, equipe.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar equipe: " + e.getMessage(), e);
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
            throw new RuntimeException("Erro ao deletar equipe: " + e.getMessage(), e);
        } finally {
            fecharRecursos(null, pstmt, conn);
        }
    }

    private void fecharRecursos(ResultSet rs, Statement stmt, Connection conn) {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (stmt != null) stmt.close(); } catch (SQLException ignored) {}
        ConexaoBanco.fechar(conn);
    }
}