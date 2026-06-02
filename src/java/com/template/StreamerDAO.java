package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamerDAO {

    private static final Logger logger = Logger.getLogger(StreamerDAO.class.getName());

    ArrayList<StreamerDTO> listaStreamers = new ArrayList<>();

    public void cadastrarStreamer (StreamerDTO streamer)
    {
        String sql = "INSERT INTO streamer (nome, conteudo, plataforma, seguidores) VALUES (?,?,?,?)";

        try (Connection c = new Conexao().conectBd(); PreparedStatement ps = c.prepareStatement(sql)){ //Try-With-Resources ele abre recursos (conexão com o banco) e fecha automaticamente no final

            ps.setString(1, streamer.getNome()); // preenche os "?" do String sql
            ps.setString(2, streamer.getConteudo());
            ps.setString(3, streamer.getPlataforma());
            ps.setInt(4, streamer.getSeguidores());
            ps.executeUpdate(); //vai executar a linha sql
        }catch (SQLException ex){ //Se der erro no banco ele avisa no console
            Logger.getLogger(StreamerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<StreamerDTO> listarStreamers ()
    {
        String sql = "Select * from streamer order by id asc";

        try (Connection c = new Conexao().conectBd(); PreparedStatement ps = c.prepareStatement(sql);

             ResultSet rs = ps.executeQuery();){ // conecta ao banco | prepara o comando sql | Executa o select e traz os dados

            while(rs.next()) //Vai percorrer todas as linhas da tabela
            {
                StreamerDTO streamer = new StreamerDTO();
                streamer.setId(rs.getInt("id"));
                streamer.setNome(rs.getString("nome"));
                streamer.setConteudo(rs.getString("conteudo"));
                streamer.setPlataforma(rs.getString("plataforma"));
                streamer.setSeguidores(rs.getInt("seguidores"));
                listaStreamers.add(streamer);
            }
        }catch (SQLException ex){
            Logger.getLogger( StreamerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaStreamers;
    }

    public void editarStreamer(StreamerDTO streamer)
    {
        String sql = "UPDATE streamer SET nome=?, conteudo=?, plataforma=?, seguidores=? WHERE id=?";

        try (Connection c = new Conexao().conectBd();PreparedStatement ps = c.prepareStatement(sql);)
        {
            ps.setString(1, streamer.getNome());
            ps.setString(2, streamer.getConteudo());
            ps.setString(3, streamer.getPlataforma());
            ps.setInt(4, streamer.getSeguidores());
            ps.setInt(5, streamer.getId());

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(StreamerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removerStreamer(int id)
    {
        String sql = "delete from streamer where id=?";

        try (Connection c = new Conexao().conectBd();
             PreparedStatement ps = c.prepareStatement(sql);)
        {
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(StreamerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
