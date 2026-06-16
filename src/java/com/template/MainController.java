package com.template;

import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class MainController {

    @FXML private Button btnExcluir;
    @FXML private Button btnCadastrar;
    @FXML private Button btnEditar;
    @FXML private Button btnLimpar;

    @FXML private TableView<InstrumentoDTO> tblInstrumento;
    @FXML private TableColumn<InstrumentoDTO, Integer> colId;
    @FXML private TableColumn<InstrumentoDTO, String> colNome;
    @FXML private TableColumn<InstrumentoDTO, String> colFamilia;
    @FXML private TableColumn<InstrumentoDTO, String> colMarca;
    @FXML private TableColumn<InstrumentoDTO, Double> colPreco;

    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtFamilia;
    @FXML private TextField txtMarca;
    @FXML private TextField txtPreco;

    // Melhoria UI: Label para mensagens de status
    @FXML private Label lblMensagem;

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        if (validarCampos()) {
            try {
                InstrumentoDTO objdto = new InstrumentoDTO();
                objdto.setNome(txtNome.getText());
                objdto.setFamilia(txtFamilia.getText());
                objdto.setMarca(txtMarca.getText());
                objdto.setPreco(Double.parseDouble(txtPreco.getText()));

                new InstrumentoDAO().inserirInstrumento(objdto);

                exibirMensagem("Cadastrado com sucesso!", true);
                carregarInstrumentos();
                limparCampos();
            } catch (Exception e) {
                exibirMensagem("Erro ao cadastrar.", false);
            }
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            exibirMensagem("Selecione um item na tabela!", false);
            return;
        }

        if (validarCampos()) {
            InstrumentoDTO objdto = new InstrumentoDTO();
            objdto.setId(Integer.parseInt(txtId.getText()));
            objdto.setNome(txtNome.getText());
            objdto.setFamilia(txtFamilia.getText());
            objdto.setMarca(txtMarca.getText());
            objdto.setPreco(Double.parseDouble(txtPreco.getText()));

            new InstrumentoDAO().atualizarInstrumento(objdto);
            exibirMensagem("Editado com sucesso!", true);
            carregarInstrumentos();
            limparCampos();
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            exibirMensagem("Selecione um item!", false);
            return;
        }

        // Melhoria UX: Confirmação antes de excluir
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmação");
        alerta.setHeaderText("Deseja realmente excluir este instrumento?");
        Optional<ButtonType> resultado = alerta.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            int id = Integer.parseInt(txtId.getText());
            new InstrumentoDAO().excluirInstrumento(id);
            exibirMensagem("Excluído com sucesso!", true);
            carregarInstrumentos();
            limparCampos();
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
        lblMensagem.setText("");
    }

    private void carregarInstrumentos() {
        ArrayList<InstrumentoDTO> lista = new InstrumentoDAO().listarInstrumentos();
        tblInstrumento.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void carregarCampos() {
        InstrumentoDTO objdto = tblInstrumento.getSelectionModel().getSelectedItem();
        if (objdto != null) {
            txtId.setText(String.valueOf(objdto.getId()));
            txtNome.setText(objdto.getNome());
            txtFamilia.setText(objdto.getFamilia());
            txtMarca.setText(objdto.getMarca());
            txtPreco.setText(String.valueOf(objdto.getPreco()));
            lblMensagem.setText("Registro selecionado.");
            lblMensagem.setTextFill(Color.WHITE);
        }
    }

    // Melhoria UX: Validação de campos e Feedback
    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtPreco.getText().isEmpty()) {
            exibirMensagem("Preencha Nome e Preço!", false);
            return false;
        }
        return true;
    }

    // Melhoria UI: Método para exibir mensagens coloridas
    private void exibirMensagem(String msg, boolean sucesso) {
        lblMensagem.setText(msg);
        lblMensagem.setTextFill(sucesso ? Color.LIGHTGREEN : Color.CHARTREUSE);
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtFamilia.clear();
        txtMarca.clear();
        txtPreco.clear();
        txtNome.requestFocus(); // Melhoria UX: Foco automático
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFamilia.setCellValueFactory(new PropertyValueFactory<>("familia"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        // Melhoria UI: Impedir edição do ID
        txtId.setEditable(false);
        txtId.setDisable(true);

        // Melhoria UX: Bloquear letras no campo de preço
        txtPreco.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*(\\.\\d*)?")) {
                txtPreco.setText(antigo);
            }
        });

        carregarInstrumentos();
    }
}