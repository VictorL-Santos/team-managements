package br.com.anhembi.view.screens;

import br.com.anhembi.controller.user.UserController;
import br.com.anhembi.model.Users;
import br.com.anhembi.service.exception.AuthenticationException;
import br.com.anhembi.service.exception.UserNotFoundException;
import br.com.anhembi.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    // --- Componentes da Interface ---
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private final UserController userController;
    private final MainFrame mainFrame;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new UserController();
        initComponents();
        addListeners();
    }

    public LoginPanel() {
        this(null);
    }

    private void addListeners() {
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
    }

    private void handleLogin() {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());

        if (login.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Login e Senha são obrigatórios.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Users authenticatedUser = this.userController.login(login, password);

            JOptionPane.showMessageDialog(this, "Login bem-sucedido! Bem-vindo, " + authenticatedUser.getLogin() + "!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            clearFields();

            if (mainFrame != null) mainFrame.showScreen("HOME");
        } catch (AuthenticationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Falha na autenticação", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegister() {
        if (this.mainFrame != null) mainFrame.showScreen("REGISTER");
    }

    private void clearFields() {
        loginField.setText("");
        passwordField.setText("");
    }

    private void initComponents() {
        // Usamos um GridBagLayout para ter mais controle sobre o posicionamento
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // --- Configurações do GridBagLayout ---
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Título ---
        JLabel titleLabel = new JLabel("Login do Sistema");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // --- Campo de Login ---
        gbc.gridwidth = 1; // Volta a ocupar uma coluna
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Login:"), gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        loginField = new JTextField(15);
        add(loginField, gbc);

        // --- Campo de Senha ---
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Senha:"), gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        // --- Painel de Botões ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("Entrar");
        registerButton = new JButton("Registrar-se");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);
    }
}