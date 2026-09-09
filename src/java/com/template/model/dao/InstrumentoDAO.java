package com.template.model.dao;

import com.template.model.Conexao;
import com.template.model.dto.InstrumentoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstrumentoDAO implements IInstrumentoDAO {

    private static final Logger logger = Logger.getLogger(InstrumentoDAO.class.getName());

    public void inserirInstrumento(InstrumentoDTO instrumento) {
        String sql = "INSERT INTO instrumentos (nome, familia, marca, preco) VALUES (?, ?, ?, ?)";

        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, instrumento.getNome());
            ps.setString(2, instrumento.getFamilia());
            ps.setString(3, instrumento.getMarca());
            ps.setDouble(4, instrumento.getPreco());

            ps.execute();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao inserir instrumento", e);
        }
    }

    public ArrayList<InstrumentoDTO> listarInstrumentos() {
        String sql = "SELECT * FROM instrumentos";
        ArrayList<InstrumentoDTO> listaInstrumentos = new ArrayList<>();

        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                InstrumentoDTO instrumento = new InstrumentoDTO();
                instrumento.setId(rs.getInt("id"));
                instrumento.setNome(rs.getString("nome"));
                instrumento.setFamilia(rs.getString("familia"));
                instrumento.setMarca(rs.getString("marca"));
                instrumento.setPreco(rs.getDouble("preco"));

                listaInstrumentos.add(instrumento);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar instrumentos", e);
        }

        return listaInstrumentos;
    }

    public void atualizarInstrumento(InstrumentoDTO instrumento) {
        String sql = "UPDATE instrumentos SET nome = ?, familia = ?, marca = ?, preco = ? WHERE id = ?";

        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, instrumento.getNome());
            ps.setString(2, instrumento.getFamilia());
            ps.setString(3, instrumento.getMarca());
            ps.setDouble(4, instrumento.getPreco());
            ps.setInt(5, instrumento.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar instrumento", e);
        }
    }

    public void excluirInstrumento(int id) {
        String sql = "DELETE FROM instrumentos WHERE id = ?";

        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao excluir instrumento", e);
        }
    }
}