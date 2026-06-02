package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController
{
    @FXML private Button btnSalvar;
    @FXML private Button btnEditar;
    @FXML private Button btnDeletar;
    @FXML private Button btnLimpar;
    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtConteudo;
    @FXML private TextField txtPlataforma;
    @FXML private TextField txtSeguidores;
    @FXML private TableView<StreamerDTO> tblStreamer;
    @FXML private TableColumn<StreamerDTO, Integer> colId;
    @FXML private TableColumn<StreamerDTO, String> colNome;
    @FXML private TableColumn<StreamerDTO, String> colConteudo;
    @FXML private TableColumn<StreamerDTO, String> colPlataforma;
    @FXML private TableColumn<StreamerDTO, Integer> colSeguidores;




    @FXML
    private void initialize()
    {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colConteudo.setCellValueFactory(new PropertyValueFactory<>("conteudo"));
        colPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        colSeguidores.setCellValueFactory(new PropertyValueFactory<>("seguidores"));
        carregarStreamer();
        System.out.println("FXML loaded successfully!");

    }

    @FXML
    private void carregarStreamer(){
       StreamerDAO objStreamerDAO = new StreamerDAO();
       ArrayList<StreamerDTO> listaStreamers = objStreamerDAO.listarStreamers();
       tblStreamer.setItems(FXCollections.observableArrayList(listaStreamers));

    }

    @FXML
    private void carregarCampos(){
        StreamerDTO objStreamerDTO = tblStreamer.getSelectionModel().getSelectedItem();

        if(objStreamerDTO != null)
        {
            txtId.setText(String.valueOf(objStreamerDTO.getId()));
            txtNome.setText(objStreamerDTO.getNome());
            txtConteudo.setText(objStreamerDTO.getConteudo());
            txtPlataforma.setText(objStreamerDTO.getPlataforma());
            txtSeguidores.setText(String.valueOf(objStreamerDTO.getSeguidores()));
        }

    }

    @FXML
    private void btnSalvarAction (ActionEvent event)
    {
        String nome = txtNome.getText();
        String conteudo = txtConteudo.getText();
        String plataforma = txtPlataforma.getText();
        Integer seguidores = Integer.parseInt(txtSeguidores.getText());

        StreamerDTO objstreamerdto = new StreamerDTO();
        objstreamerdto.setNome(nome);
        objstreamerdto.setConteudo(conteudo);
        objstreamerdto.setPlataforma(plataforma);
        objstreamerdto.setSeguidores(seguidores);

        StreamerDAO objstreamerdao = new StreamerDAO();
        objstreamerdao.cadastrarStreamer(objstreamerdto);

        carregarStreamer();
    }

    @FXML
    private void btnEditarAction (ActionEvent event)
    {
        Integer id = Integer.parseInt(txtId.getText());
        String nome = txtNome.getText();
        String conteudo = txtConteudo.getText();
        String plataforma = txtPlataforma.getText();
        Integer seguidores = Integer.parseInt(txtSeguidores.getText());

        StreamerDTO objstreamerdto = new StreamerDTO();
        objstreamerdto.setId(id); // Guarda o ID no objeto para saber quem alterar
        objstreamerdto.setNome(nome);
        objstreamerdto.setConteudo(conteudo);
        objstreamerdto.setPlataforma(plataforma);
        objstreamerdto.setSeguidores(seguidores);

        StreamerDAO objstreamerdao = new StreamerDAO();
        objstreamerdao.editarStreamer(objstreamerdto); // Executa a alteração no banco de dados

        carregarStreamer(); // Recarrega a tabela de streamers na tela
        btnLimparAction(event); // Limpa os campos após a edição
    }

    @FXML
    private void btnLimparAction (ActionEvent event)
    {
        txtId.setText(""); // Apaga o texto do campo ID
        txtNome.setText(""); // Apaga o texto do campo Nome
        txtConteudo.setText(""); // Apaga o texto do campo Conteúdo
        txtPlataforma.setText(""); // Apaga o texto do campo Plataforma
        txtSeguidores.setText(""); // Apaga o texto do campo Seguidores
    }

    @FXML
    private void btnDeletarAction (ActionEvent event)
    {
        Integer id = Integer.parseInt(txtId.getText()); // Pega o ID do streamer selecionado

        StreamerDAO objstreamerdao = new StreamerDAO();
        objstreamerdao.removerStreamer(id); // Remove o streamer do banco de dados usando o ID

        carregarStreamer(); // Recarrega a tabela de streamers na tela
        btnLimparAction(event); // Limpa os campos após a exclusão
    }
}
