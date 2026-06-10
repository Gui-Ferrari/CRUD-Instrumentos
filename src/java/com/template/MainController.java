package com.template;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML private Button btnExcluir;
    @FXML private Button btnCadastrar; // Ajustado de btnAdicionar para btnCadastrar igual ao FXML
    @FXML private Button btnEditar;
    @FXML private Button btnLimpar;

    // Componentes mapeados do FXML
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

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        String nome = txtNome.getText();
        String familia = txtFamilia.getText();
        String marca = txtMarca.getText();

        if(nome.isEmpty() || txtPreco.getText().isEmpty()) return; // Proteção contra campos vazios

        double preco = Double.parseDouble(txtPreco.getText());

        InstrumentoDTO objdto = new InstrumentoDTO();
        objdto.setNome(nome);
        objdto.setFamilia(familia);
        objdto.setMarca(marca);
        objdto.setPreco(preco);
// foda
        InstrumentoDAO objdao = new InstrumentoDAO();
        objdao.inserirInstrumento(objdto);

        carregarInstrumentos();
        limparCampos();
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        if(txtId.getText().isEmpty()) return; // Proteção para não editar sem selecionar da tabela

        int id = Integer.parseInt(txtId.getText());
        String nome = txtNome.getText();
        String familia = txtFamilia.getText();
        String marca = txtMarca.getText();
        double preco = Double.parseDouble(txtPreco.getText());

        InstrumentoDTO objdto = new InstrumentoDTO();
        objdto.setId(id);
        objdto.setNome(nome);
        objdto.setFamilia(familia);
        objdto.setMarca(marca);
        objdto.setPreco(preco);

        InstrumentoDAO objdao = new InstrumentoDAO();
        objdao.atualizarInstrumento(objdto);

        carregarInstrumentos();
        limparCampos();
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        if(txtId.getText().isEmpty()) return;

        int id = Integer.parseInt(txtId.getText());

        InstrumentoDAO objdao = new InstrumentoDAO();
        objdao.excluirInstrumento(id);

        limparCampos();
        carregarInstrumentos();
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
    }

    @FXML
    private void carregarInstrumentos() {
        InstrumentoDAO objdao = new InstrumentoDAO();
        ArrayList<InstrumentoDTO> lista = objdao.listarInstrumentos();

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
        }
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtFamilia.clear();
        txtMarca.clear();
        txtPreco.clear();
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFamilia.setCellValueFactory(new PropertyValueFactory<>("familia"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        carregarInstrumentos();
    }
}