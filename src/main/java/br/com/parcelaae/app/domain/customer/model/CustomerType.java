package br.com.parcelaae.app.domain.customer.model;

import java.util.stream.Stream;

public enum CustomerType {

    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private int cod;
    private String descricao;

    CustomerType(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }
    public String getDescricao() {
        return descricao;
    }

    public static CustomerType toEnum(Integer cod) {
        return Stream.of(CustomerType.values())
                .filter(e -> cod.equals(e.getCod()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + cod));
    }
}
