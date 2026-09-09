package com.template.validator;

import com.template.model.dto.InstrumentoDTO;
import java.util.ArrayList;
import java.util.List;

public class InstrumentoValidator implements IInstrumentoValidator {

    @Override
    public void validar(InstrumentoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Objeto de instrumento não informado.");
        }

        List<Validador<?>> validadores = new ArrayList<>();
        validadores.add(new CampoObrigatorioValidador("Nome", dto.getNome()));
        validadores.add(new CampoObrigatorioValidador("Família", dto.getFamilia()));
        validadores.add(new CampoObrigatorioValidador("Marca", dto.getMarca()));
        validadores.add(new PrecoValidator(dto.getPreco()));

        for (Validador validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                throw new IllegalArgumentException(validador.getMensagemErro());
            }
        }
    }
}