package br.com.anhembi.model;

import br.com.anhembi.model.enums.UserProfileEnum;

public abstract class Users {
    private String login;
    private String email;
    private String password;
    private final String cpf;
    private final UserProfileEnum role;

    public Users(String login, String password, String cpf, UserProfileEnum role, String email) {
        this.login = login;
        this.password = password;
        this.cpf = cpf;
        this.role = role;
        this.email = email;
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
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}
