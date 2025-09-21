package br.com.anhembi.model.enums;

public enum UserProfileEnum {
    ADMIN("Administrador"),
    TECH_LEAD("Gestor Técnico"),
    DEVELOPER("Desenvolvedor"),
    PRODUCT_OWNER("Gestor de Produto");

    private final String description;

    UserProfileEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
