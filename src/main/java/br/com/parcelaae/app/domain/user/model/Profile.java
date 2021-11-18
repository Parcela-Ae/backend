package br.com.parcelaae.app.domain.user.model;

import java.util.stream.Stream;

public enum Profile {

    ADMIN(1, "ROLE_ADMIN"),
    CUSTOMER(2, "ROLE_CUSTOMER"),
    CLINIC(3, "ROLE_CLINIC");

    private int cod;
    private String description;

    Profile(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }
    public String getDescription() {
        return description;
    }

    public static Profile toEnum(Integer cod) {
        return Stream.of(Profile.values())
                .filter(e -> cod.equals(e.getCod()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + cod));
    }
}
