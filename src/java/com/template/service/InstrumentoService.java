package com.template.service;

import com.template.model.dao.InstrumentoDAO;
import com.template.model.dto.InstrumentoDTO;
import com.template.validator.InstrumentoValidator;

import java.util.ArrayList;

public class InstrumentoService {

    private final InstrumentoDAO dao = new InstrumentoDAO();

    public ArrayList<InstrumentoDTO> listar() {
        return dao.listarInstrumentos();
    }

    public void cadastrar(InstrumentoDTO dto) {
        InstrumentoValidator.validar(dto);
        dao.inserirInstrumento(dto);
    }

    public void atualizar(InstrumentoDTO dto) {
        if (dto.getId() <= 0) {
            throw new IllegalArgumentException("Selecione um item válido para editar!");
        }
        InstrumentoValidator.validar(dto);
        dao.atualizarInstrumento(dto);
    }

    public void excluir(String idText) {
        if (idText == null || idText.trim().isEmpty()) {
            throw new IllegalArgumentException("Selecione um item na tabela para excluir!");
        }

        try {
            int id = Integer.parseInt(idText);
            dao.excluirInstrumento(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido para exclusão.");
        }
    }
}