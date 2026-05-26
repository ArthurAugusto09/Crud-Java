package com.template;

public class StreamerDAO {
    public void cadastrarStreamer (StreamerDTO streamer)
    {
        String sql = "INSERT INTO streamer (nome, conteudo, plataforma_transmissao, qtnd_seguidores) VALUES (?,?,?,?)";

        try (Connection c = new Conexao().conectBd(); PreparedStatement ps = c.prepareStatement(sql)){ //Try-With-Resources ele abre recursos (conexão com o banco) e fecha automaticamente no final

            ps.setString(1, streamer.getNome()); // preenche os "?" do String sql
            ps.setString(2, streamer.getConteudo());
            ps.setString(3, streamer.getPlataformaTransmissao());
            ps.setInt(4, streamer.getQtndSeguidores());
            ps.executeUpdate(); //vai executar a linha sql
        }catch (SQLException ex){ //Se der erro no banco ele avisa no console
            Logger.getLogger(StreamerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selecionarStreamer ()
    {
        String sql = "Select * from streamer order by id asc";

        try (Connection c = new Conexao().conectBd(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery();){ // conecta ao banco | prepara o comando sql | Executa o select e traz os dados

            while(rs.next()) //Vai percorrer todas as linhas da tabela
            {
                System.out.println("ID: " + rs.getInt("id")+"\nNOME: "+ rs.getString("nome")+"\nCONTEUDO: "+rs.getString("conteudo")+"\nPLATAFORMA: "+rs.getString("plataforma_transmissao")+"\nSEGUIDORES: "+rs.getInt("qtnd_seguidores")+"\n");
            }
        }catch (SQLException ex){
            Logger.getLogger( StreamerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizarStreamer(StreamerDTO streamer)
    {
        String sql = "UPDATE streamer SET nome=?, conteudo=?, plataforma_transmissao=?, qtnd_seguidores=? WHERE id=?";

        try (Connection c = new Conexao().conectBd();PreparedStatement ps = c.prepareStatement(sql);)
        {
            ps.setString(1, streamer.getNome());
            ps.setString(2, streamer.getConteudo());
            ps.setString(3, streamer.getPlataformaTransmissao());
            ps.setInt(4, streamer.getQtndSeguidores());
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
