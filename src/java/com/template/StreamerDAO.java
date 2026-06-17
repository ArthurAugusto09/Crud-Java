package com.template;

import java.sql.*;
import java.util.ArrayList;

public class StreamerDAO {

    public void cadastrarStreamer(StreamerDTO s) {
        String sql = "INSERT INTO streamer (nome, conteudo, plataforma, seguidores) VALUES (?,?,?,?)";
        try (Connection c = new Conexao().conectBd(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getNome());
            ps.setString(2, s.getConteudo());
            ps.setString(3, s.getPlataforma());
            ps.setInt(4, s.getSeguidores());
            ps.executeUpdate();
        } catch (SQLException ignored) {}
    }

    public ArrayList<StreamerDTO> listarStreamers() {
        ArrayList<StreamerDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM streamer ORDER BY id ASC";
        try (Connection c = new Conexao().conectBd(); ResultSet rs = c.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                StreamerDTO s = new StreamerDTO();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setConteudo(rs.getString("conteudo"));
                s.setPlataforma(rs.getString("plataforma"));
                s.setSeguidores(rs.getInt("seguidores"));
                lista.add(s);
            }
        } catch (SQLException ignored) {}
        return lista;
    }

    public void editarStreamer(StreamerDTO s) {
        String sql = "UPDATE streamer SET nome=?, conteudo=?, plataforma=?, seguidores=? WHERE id=?";
        try (Connection c = new Conexao().conectBd(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getNome());
            ps.setString(2, s.getConteudo());
            ps.setString(3, s.getPlataforma());
            ps.setInt(4, s.getSeguidores());
            ps.setInt(5, s.getId());
            ps.executeUpdate();
        } catch (SQLException ignored) {}
    }

    public void removerStreamer(int id) {
        String sql = "DELETE FROM streamer WHERE id=?";
        try (Connection c = new Conexao().conectBd(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ignored) {}
    }
}