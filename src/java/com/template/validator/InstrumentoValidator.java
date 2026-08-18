package com.template.validator;

import com.template.model.dto.InstrumentoDTO;

public class InstrumentoValidator {

    public static void validar(InstrumentoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Objeto de instrumento não informado.");
        }

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo Nome é obrigatório!");
        }

        if (dto.getPreco() <= 0) {
            throw new IllegalArgumentException("Informe um Preço válido maior que zero!");
        }
    }
}