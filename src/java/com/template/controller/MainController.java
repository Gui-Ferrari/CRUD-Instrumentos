package com.template.controller; // Subpacote do Controller

// Importações dos seus subpacotes
import com.template.model.dao.InstrumentoDAO;
import com.template.model.dto.InstrumentoDTO;
import com.template.util.AlertaUtil;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class MainController {

    @FXML private Button btnCadastrar;
    @FXML private Button btnEditar;
    @FXML private Button btnExcluir;
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

    @FXML private Label lblMensagem;

    private final InstrumentoDAO instrumentoDAO = new InstrumentoDAO();

    @FXML
    public void initialize() {
        configurarTabela();
        configurarMascaraPreco();
        configurarCampoId();
        carregarInstrumentos();
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        if (!validarCampos()) return;

        try {
            InstrumentoDTO objdto = extrairDTOFormulario(false);
            instrumentoDAO.inserirInstrumento(objdto);

            exibirMensagem("Instrumento cadastrado com sucesso!", true);
            carregarInstrumentos();
            limparCampos();
        } catch (Exception e) {
            exibirMensagem("Erro ao cadastrar instrumento.", false);
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            exibirMensagem("Selecione um item na tabela para editar!", false);
            return;
        }

        if (!validarCampos()) return;

        try {
            InstrumentoDTO objdto = extrairDTOFormulario(true);
            instrumentoDAO.atualizarInstrumento(objdto);

            exibirMensagem("Instrumento atualizado com sucesso!", true);
            carregarInstrumentos();
            limparCampos();
        } catch (Exception e) {
            exibirMensagem("Erro ao atualizar instrumento.", false);
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            exibirMensagem("Selecione um item na tabela para excluir!", false);
            return;
        }

        boolean confirmou = AlertaUtil.confirmar(
                "Confirmação de Exclusão",
                "Deseja realmente excluir este instrumento?"
        );

        if (confirmou) {
            try {
                int id = Integer.parseInt(txtId.getText());
                instrumentoDAO.excluirInstrumento(id);

                exibirMensagem("Instrumento excluído com sucesso!", true);
                carregarInstrumentos();
                limparCampos();
            } catch (Exception e) {
                exibirMensagem("Erro ao excluir instrumento.", false);
            }
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
        lblMensagem.setText("");
    }

    @FXML
    private void carregarCampos() {
        InstrumentoDTO selecionado = tblInstrumento.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            txtId.setText(String.valueOf(selecionado.getId()));
            txtNome.setText(selecionado.getNome());
            txtFamilia.setText(selecionado.getFamilia());
            txtMarca.setText(selecionado.getMarca());
            txtPreco.setText(String.valueOf(selecionado.getPreco()));

            exibirMensagem("Registro selecionado.", true);
        }
    }

    private void carregarInstrumentos() {
        ArrayList<InstrumentoDTO> lista = instrumentoDAO.listarInstrumentos();
        tblInstrumento.setItems(FXCollections.observableArrayList(lista));
    }

    private InstrumentoDTO extrairDTOFormulario(boolean incluirId) {
        InstrumentoDTO dto = new InstrumentoDTO();
        if (incluirId && !txtId.getText().isEmpty()) {
            dto.setId(Integer.parseInt(txtId.getText()));
        }
        dto.setNome(txtNome.getText().trim());
        dto.setFamilia(txtFamilia.getText().trim());
        dto.setMarca(txtMarca.getText().trim());
        dto.setPreco(Double.parseDouble(txtPreco.getText().trim()));
        return dto;
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty() || txtPreco.getText().trim().isEmpty()) {
            exibirMensagem("Campos Nome e Preço são obrigatórios!", false);
            return false;
        }
        return true;
    }

    private void exibirMensagem(String msg, boolean sucesso) {
        lblMensagem.setText(msg);
        lblMensagem.setTextFill(sucesso ? Color.web("#2ecc71") : Color.web("#e74c3c"));
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtFamilia.clear();
        txtMarca.clear();
        txtPreco.clear();
        tblInstrumento.getSelectionModel().clearSelection();
        txtNome.requestFocus();
    }

    private void configurarTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFamilia.setCellValueFactory(new PropertyValueFactory<>("familia"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tblInstrumento.setPlaceholder(new Label("Nenhum instrumento cadastrado no momento."));
    }

    private void configurarCampoId() {
        txtId.setEditable(false);
        txtId.setDisable(true);
    }

    private void configurarMascaraPreco() {
        txtPreco.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*(\\.\\d*)?")) {
                txtPreco.setText(antigo);
            }
        });
    }
}