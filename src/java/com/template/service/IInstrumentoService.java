package com.template.service;

import com.template.model.dto.InstrumentoDTO;
import java.util.ArrayList;

public interface IInstrumentoService {
    ArrayList<InstrumentoDTO> listar();
    void cadastrar(InstrumentoDTO dto);
    void atualizar(InstrumentoDTO dto);
    void excluir(String idText);
}