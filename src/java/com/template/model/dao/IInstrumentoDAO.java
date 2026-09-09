package com.template.model.dao;

import com.template.model.dto.InstrumentoDTO;
import java.util.ArrayList;

public interface IInstrumentoDAO {
    void inserirInstrumento(InstrumentoDTO instrumento);
    ArrayList<InstrumentoDTO> listarInstrumentos();
    void atualizarInstrumento(InstrumentoDTO instrumento);
    void excluirInstrumento(int id);
}