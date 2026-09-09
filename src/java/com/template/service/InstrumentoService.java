package com.template.service;

import com.template.model.dao.IInstrumentoDAO;
import com.template.model.dto.InstrumentoDTO;
import com.template.validator.IInstrumentoValidator;
import java.util.ArrayList;

public class InstrumentoService implements IInstrumentoService {

    private final IInstrumentoDAO dao;
    private final IInstrumentoValidator validator;

    // Recebe abstrações por construtor (DIP)
    public InstrumentoService(IInstrumentoDAO dao, IInstrumentoValidator validator) {
        this.dao = dao;
        this.validator = validator;
    }

    @Override
    public ArrayList<InstrumentoDTO> listar() {
        return dao.listarInstrumentos();
    }

    @Override
    public void cadastrar(InstrumentoDTO dto) {
        validator.validar(dto);
        dao.inserirInstrumento(dto);
    }

    @Override
    public void atualizar(InstrumentoDTO dto) {
        if (dto.getId() <= 0) {
            throw new IllegalArgumentException("Selecione um item válido para editar!");
        }
        validator.validar(dto);
        dao.atualizarInstrumento(dto);
    }

    @Override
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