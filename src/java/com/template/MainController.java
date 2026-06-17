package com.template;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class MainController {

    @FXML private TextField txtNome, txtConteudo;
    @FXML private ComboBox<String> cbPlataforma;
    @FXML private Spinner<Integer> spSeguidores;
    @FXML private Button btnSalvar, btnEditar, btnDeletar, btnLimpar;
    @FXML private Label lblMensagem, lblContador;

    @FXML private TableView<StreamerDTO> tblStreamer;
    @FXML private TableColumn<StreamerDTO, Integer> colId;
    @FXML private TableColumn<StreamerDTO, String> colNome, colConteudo, colPlataforma;
    @FXML private TableColumn<StreamerDTO, Integer> colSeguidores;

    private int idEditando = -1;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colConteudo.setCellValueFactory(new PropertyValueFactory<>("conteudo"));
        colPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        colSeguidores.setCellValueFactory(new PropertyValueFactory<>("seguidores"));

        cbPlataforma.setItems(FXCollections.observableArrayList("Twitch", "Youtube", "Tiktok", "Kick", "Outra"));
        spSeguidores.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999999999, 0));

        //Bloqueia a digitação de letras e de símbolos (como o "-" de negativo)
        spSeguidores.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                // Substitui tudo que não for dígito (0 a 9) por vazio
                spSeguidores.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        limpar();
    }

    @FXML
    private void salvar() {
        if (!validar()) return;

        new StreamerDAO().cadastrarStreamer(montarDTO());
        mensagem("Cadastrado com sucesso!", true);
        limpar();
    }

    @FXML
    private void editar() {
        if (!validar() || idEditando == -1) return;

        StreamerDTO dto = montarDTO();
        dto.setId(idEditando);

        new StreamerDAO().editarStreamer(dto);
        mensagem("Editado com sucesso!", true);
        limpar();
    }

    @FXML
    private void deletar() {
        if (idEditando == -1) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir este streamer?");
        alert.setHeaderText("Confirmação de Exclusão");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            new StreamerDAO().removerStreamer(idEditando);
            mensagem("Excluído com sucesso!", true);
            limpar();
        }
    }

    @FXML
    private void limpar() {
        txtNome.clear();
        txtConteudo.clear();
        cbPlataforma.setValue(null);
        spSeguidores.getValueFactory().setValue(0);
        idEditando = -1;
        tblStreamer.getSelectionModel().clearSelection();

        btnSalvar.setDisable(false);
        btnEditar.setDisable(true);
        btnDeletar.setDisable(true);

        carregarTabela();
    }

    @FXML
    private void carregarCampos() {
        StreamerDTO selecionado = tblStreamer.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            idEditando = selecionado.getId();
            txtNome.setText(selecionado.getNome());
            txtConteudo.setText(selecionado.getConteudo());
            cbPlataforma.setValue(selecionado.getPlataforma());
            spSeguidores.getValueFactory().setValue(selecionado.getSeguidores());

            btnSalvar.setDisable(true);
            btnEditar.setDisable(false);
            btnDeletar.setDisable(false);
            lblMensagem.setText("");
        }
    }

    private StreamerDTO montarDTO() {
        StreamerDTO s = new StreamerDTO();
        s.setNome(txtNome.getText());
        s.setConteudo(txtConteudo.getText());
        s.setPlataforma(cbPlataforma.getValue());

        // Pega o valor do campo (caso o usuário tenha digitado mas não clicado fora)
        try {
            int seguidoresDigitados = Integer.parseInt(spSeguidores.getEditor().getText());
            s.setSeguidores(seguidoresDigitados);
        } catch (NumberFormatException e) {
            s.setSeguidores(0); // Valor padrão de segurança
        }

        return s;
    }

    private boolean validar() {
        if (txtNome.getText().trim().isEmpty() ||
                txtConteudo.getText().trim().isEmpty() ||
                cbPlataforma.getValue() == null) {

            mensagem("Erro: Todos os campos são obrigatórios!", false);
            return false;
        }
        return true;
    }

    private void mensagem(String texto, boolean sucesso) {
        lblMensagem.setText(texto);
        lblMensagem.setTextFill(sucesso ? Color.web("#00e676") : Color.web("#ff5252"));
    }

    private void carregarTabela() {
        var lista = new StreamerDAO().listarStreamers();
        tblStreamer.setItems(FXCollections.observableArrayList(lista));
        lblContador.setText("Total: " + lista.size() + " streamers");
    }
}