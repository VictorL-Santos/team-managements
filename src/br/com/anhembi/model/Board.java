package br.com.anhembi.model;

import java.util.UUID;

public abstract class Board {
    private String title;
    private String description;
    private final UUID id;

    public Board(String title, String description) {
        this.title = title;
        this.id = UUID.randomUUID();
        this.description = description;
    }

    public Board(UUID id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // GETTERS \\
    public UUID getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    // SETTERS \\
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board that = (Board) o;
        return this.id.equals(that.id);
    }

    @Override
    public final int hashCode() {
        return this.id.hashCode();
    }
}
