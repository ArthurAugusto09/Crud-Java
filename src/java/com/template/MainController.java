package com.template;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class MainController
{
    @FXML private Button btnSalvar;
    @FXML private Button btnEditar;
    @FXML private Button btnDeletar;
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
        System.out.println("FXML loaded successfully!");
    }
}
