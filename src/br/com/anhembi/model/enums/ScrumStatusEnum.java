package br.com.anhembi.model.enums;

public enum ScrumStatusEnum {
    TO_DO("Não Iniciada"),
    ON_GOING("Em Andamento"),
    IN_REVIEW("Em Revisão"),
    DONE("Concluído");

    private final String description;

    ScrumStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
