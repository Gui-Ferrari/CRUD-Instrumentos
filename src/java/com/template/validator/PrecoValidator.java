package com.template.validator;

public class PrecoValidator implements Validador<Double> {
    private final Double preco;

    public PrecoValidator(Double preco) {
        this.preco = preco;
    }

    @Override
    public boolean validar(Double valorAtual) {
        return this.preco != null && this.preco > 0;
    }

    @Override
    public String getMensagemErro() {
        return "O preço deve ser um valor maior que zero!";
    }

    @Override
    public Double getValor() {
        return preco;
    }
}