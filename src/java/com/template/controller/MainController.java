package com.template.controller;

import com.template.model.dto.InstrumentoDTO;
import com.template.service.InstrumentoService;
import com.template.util.AlertaUtil;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class MainController {

    // --- Componentes visuais ---
    @FXML private Button btnCadastrar, btnEditar, btnExcluir, btnLimpar;
    @FXML private TableView<InstrumentoDTO> tblInstrumento;
    @FXML private TableColumn<InstrumentoDTO, Integer> colId;
    @FXML private TableColumn<InstrumentoDTO, String> colNome, colFamilia, colMarca;
    @FXML private TableColumn<InstrumentoDTO, Double> colPreco;
    @FXML private TextField txtId, txtNome, txtFamilia, txtMarca, txtPreco;
    @FXML private Label lblMensagem;

    // Apenas a camada de Serviço é injetada
    private final InstrumentoService instrumentoService = new InstrumentoService();

    @FXML
    public void initialize() {
        configurarTabela();
        configurarMascaraPreco();
        configurarCampoId();
        carregarTabela();
    }

    // ==========================================
    // TRATADORES DE EVENTOS (Ações do Usuário)
    // ==========================================

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        try {
            InstrumentoDTO dto = extrairDTODoFormulario(false);
            instrumentoService.cadastrar(dto);

            exibirMensagem("Instrumento cadastrado com sucesso!", true);
            carregarTabela();
            limparFormulario();
        } catch (IllegalArgumentException e) {
            exibirMensagem(e.getMessage(), false);
        } catch (Exception e) {
            exibirMensagem("Erro inesperado ao cadastrar.", false);
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        try {
            InstrumentoDTO dto = extrairDTODoFormulario(true);
            instrumentoService.atualizar(dto);

            exibirMensagem("Instrumento atualizado com sucesso!", true);
            carregarTabela();
            limparFormulario();
        } catch (IllegalArgumentException e) {
            exibirMensagem(e.getMessage(), false);
        } catch (Exception e) {
            exibirMensagem("Erro inesperado ao atualizar.", false);
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        try {
            if (txtId.getText().isEmpty()) {
                exibirMensagem("Selecione um item na tabela para excluir!", false);
                return;
            }

            boolean confirmou = AlertaUtil.confirmar(
                    "Confirmação de Exclusão",
                    "Deseja realmente excluir este instrumento?"
            );

            if (confirmou) {
                instrumentoService.excluir(txtId.getText());
                exibirMensagem("Instrumento excluído com sucesso!", true);
                carregarTabela();
                limparFormulario();
            }
        } catch (IllegalArgumentException e) {
            exibirMensagem(e.getMessage(), false);
        } catch (Exception e) {
            exibirMensagem("Erro inesperado ao excluir.", false);
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparFormulario();
        lblMensagem.setText("");
    }

    @FXML
    private void carregarCampos() {
        InstrumentoDTO selecionado = tblInstrumento.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            preencherFormulario(selecionado);
            exibirMensagem("Registro selecionado.", true);
        }
    }

    // ==========================================
    // MÉTODOS EXCLUSIVOS DE MANIPULAÇÃO DE TELA (UI)
    // ==========================================

    private void carregarTabela() {
        tblInstrumento.setItems(FXCollections.observableArrayList(instrumentoService.listar()));
    }

    private InstrumentoDTO extrairDTODoFormulario(boolean incluirId) {
        InstrumentoDTO dto = new InstrumentoDTO();
        if (incluirId && !txtId.getText().isEmpty()) {
            dto.setId(Integer.parseInt(txtId.getText()));
        }
        dto.setNome(txtNome.getText().trim());
        dto.setFamilia(txtFamilia.getText().trim());
        dto.setMarca(txtMarca.getText().trim());

        try {
            dto.setPreco(Double.parseDouble(txtPreco.getText().trim()));
        } catch (NumberFormatException e) {
            dto.setPreco(0.0); // Preço inválido cairá na validação do Service
        }
        return dto;
    }

    private void preencherFormulario(InstrumentoDTO dto) {
        txtId.setText(String.valueOf(dto.getId()));
        txtNome.setText(dto.getNome());
        txtFamilia.setText(dto.getFamilia());
        txtMarca.setText(dto.getMarca());
        txtPreco.setText(String.valueOf(dto.getPreco()));
    }

    private void limparFormulario() {
        txtId.clear();
        txtNome.clear();
        txtFamilia.clear();
        txtMarca.clear();
        txtPreco.clear();
        tblInstrumento.getSelectionModel().clearSelection();
        txtNome.requestFocus();
    }

    private void exibirMensagem(String msg, boolean sucesso) {
        lblMensagem.setText(msg);
        lblMensagem.setTextFill(sucesso ? Color.web("#2ecc71") : Color.web("#e74c3c"));
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