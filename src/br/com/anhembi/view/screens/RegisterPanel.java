package br.com.anhembi.view.screens;

import br.com.anhembi.controller.user.UserController;
import br.com.anhembi.model.enums.UserProfileEnum;
import br.com.anhembi.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private JTextField loginField;
    private JTextField emailField;
    private JTextField cpfField;
    private JComboBox<UserProfileEnum> roleComboBox;
    private JPasswordField passwordField;
    private JButton cancelButton;
    private JButton registerButton;

    private final UserController userController;
    private final MainFrame mainFrame;

    public RegisterPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new UserController();
        initComponents();
        addListeners();
    }

    public RegisterPanel() {
        this(null);
    }

    private void addListeners() {
        registerButton.addActionListener(e -> handleRegister());
        cancelButton.addActionListener(e -> handleCancel());
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // --- Configurações do GridBagLayout ---
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Título ---
        JLabel titleLabel = new JLabel("Registro de Novo Usuário");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // --- Campo de Login ---
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Login:"), gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        loginField = new JTextField(15);
        add(loginField, gbc);

        // --- Campo de Email ---
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("E-mail:"), gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        emailField = new JTextField(15);
        add(emailField, gbc);

        // --- Campo de CPF ---
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("CPF:"), gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        cpfField = new JTextField(15);
        add(cpfField, gbc);


        // --- Campo de Senha ---
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Senha:"), gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        // --- Linha 5: Campo de Perfil (Role) ---
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Perfil:"), gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        roleComboBox = new JComboBox<>(new UserProfileEnum[]{
                UserProfileEnum.DEVELOPER,
                UserProfileEnum.TECH_LEAD,
                UserProfileEnum.PRODUCT_OWNER
        });
        add(roleComboBox, gbc);

        // --- Painel de Botões ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cancelButton = new JButton("Cancelar");
        registerButton = new JButton("Registrar-se");
        buttonPanel.add(cancelButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);
    }

    private void handleRegister() {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        String cpf = cpfField.getText();
        String email = emailField.getText();
        UserProfileEnum selectedRole = (UserProfileEnum) roleComboBox.getSelectedItem();

        try {
            userController.saveUser(login, password, cpf, email, selectedRole);

            JOptionPane.showMessageDialog(this, "Usuário salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            clearFields();

            if (mainFrame != null) mainFrame.showScreen("LOGIN");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancel() {
        if (mainFrame != null) mainFrame.showScreen("LOGIN");
    }

    private void clearFields() {
        loginField.setText("");
        passwordField.setText("");
        emailField.setText("");
        cpfField.setText("");
        roleComboBox.setSelectedIndex(0);
    }
}
