package br.com.anhembi.model;

import br.com.anhembi.model.enums.UserProfileEnum;

import java.util.Objects;

public abstract class Users {
    private Long id;
    private String login;
    private String email;
    private final String cpf;
    private final String password;
    private final UserProfileEnum role;

    public Users(Long id, String login, String password, String cpf, UserProfileEnum role, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.cpf = cpf;
        this.role = role;
        this.email = email;
    }

    public Users(String login, String password, String cpf, UserProfileEnum role, String email) {
        this(null, login, password, cpf, role, email);
    }

    // --- GETTER PARA TODOS OS CAMPOS --- \\
    public String getLogin() {
        return login;
    }
    public String getCpf() {
        return cpf;
    }
    public UserProfileEnum getRole() {
        return role;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    // -- SETTER PARA CAMPOS MUTÁVEIS -- \\
    public void setLogin(String newLogin) {
        this.login = newLogin;
    }
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Users users = (Users) o;
        return this.id != null && this.id.equals(users.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
