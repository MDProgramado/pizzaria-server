package com.br.pizzaria.core.domain.enums;

public enum PaymentMethod {
    CREDIT_CARD("Cartão de Credito"),
    DEBIT_CARD("Cartão de Debito"),
    PIX("Pix"),
    BANK_SLIP("Boleto");

    private final String description;

    PaymentMethod(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
