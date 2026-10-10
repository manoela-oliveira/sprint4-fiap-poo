package br.com.sistemamotiva.dao;

import br.com.sistemamotiva.db.ConexaoBanco;
import br.com.sistemamotiva.model.IntervencaoOperacional;
import br.com.sistemamotiva.model.Pulverizacao;
import br.com.sistemamotiva.model.RocadaMecanizada;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntervencaoOperacionalDAO {

    private static final String SQL_INSERT = 
        "INSERT INTO intervencoes (tipo, descricao, custo_estimado) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM intervencoes WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM intervencoes ORDER BY id";
    private static final String SQL_UPDATE = 
        "UPDATE intervencoes SET descricao = ?, custo_estimado = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM intervencoes WHERE id = ?";

    public IntervencaoOperacionalDAO() {}

    public void inserir(IntervencaoOperacional intervencao) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoBanco.getConexao();
            pstmt = conn.prepareStatement(SQL_INSERT, new String[]{"ID"});
            pstmt.setString(1, intervencao.getTipo());
            pstmt.setString(2, intervencao.getDescricao());
            pstmt.setDouble(3, intervencao.getCustoEstimado());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    intervencao.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir intervenção: " + e.getMessage(), e);
        } finally {
            fecharRecursos(rs, pstmt, conn);
        }
    }

    public IntervencaoOperacional buscarPorId(Long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoBanco.getConexao();
            pstmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return extrairIntervencao(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar intervenção: " + e.getMessage(), e);
        } finally {
            fecharRecursos(rs, pstmt, conn);
        }
    }

    public List<IntervencaoOperacional> listarTodas() {
        List<IntervencaoOperacional> lista = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_SELECT_ALL);
            while (rs.next()) {
                lista.add(extrairIntervencao(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar intervenções: " + e.getMessage(), e);
        } finally {
            fecharRecursos(rs, stmt, conn);
        }
    }

    public void atualizar(IntervencaoOperacional intervencao) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoBanco.getConexao();
            pstmt = conn.prepareStatement(SQL_UPDATE);
            pstmt.setString(1, intervencao.getDescricao());
            pstmt.setDouble(2, intervencao.getCustoEstimado());
            pstmt.setLong(3, intervencao.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar intervenção: " + e.getMessage(), e);
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
            throw new RuntimeException("Erro ao deletar intervenção: " + e.getMessage(), e);
        } finally {
            fecharRecursos(null, pstmt, conn);
        }
    }

    private IntervencaoOperacional extrairIntervencao(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        String desc = rs.getString("descricao");
        double custo = rs.getDouble("custo_estimado");
        IntervencaoOperacional interv;

        if ("ROCADA".equalsIgnoreCase(tipo)) {
            interv = new RocadaMecanizada(desc, custo);
        } else {
            interv = new Pulverizacao(desc, custo);
        }
        interv.setId(rs.getLong("id"));
        return interv;
    }

    private void fecharRecursos(ResultSet rs, Statement stmt, Connection conn) {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (stmt != null) stmt.close(); } catch (SQLException ignored) {}
        ConexaoBanco.fechar(conn);
    }
}