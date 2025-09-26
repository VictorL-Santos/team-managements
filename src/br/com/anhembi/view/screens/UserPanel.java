package br.com.anhembi.view.screens;

import br.com.anhembi.controller.user.UserController;
import br.com.anhembi.model.Users;
import br.com.anhembi.model.enums.UserProfileEnum;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserPanel extends JPanel {

    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField cpfField;
    private JTextField emailField;
    private JComboBox<UserProfileEnum> roleComboBox;
    private JButton saveButton;
    private JTextArea userListArea;

    private final UserController userController;

    public UserPanel() {
        this.userController = new UserController();
        initComponents();
        addListeners();
        refreshUserList();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Novo Usuário"));

        formPanel.add(new JLabel("Login:"));
        loginField = new JTextField();
        formPanel.add(loginField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        formPanel.add(cpfField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Perfil:"));
        roleComboBox = new JComboBox<>(UserProfileEnum.values());
        formPanel.add(roleComboBox);

        saveButton = new JButton("Salvar Usuário");
        formPanel.add(new JLabel());
        formPanel.add(saveButton);

        userListArea = new JTextArea();
        userListArea.setEditable(false);
        userListArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(userListArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Usuários Cadastrados"));

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addListeners() {
        saveButton.addActionListener(e -> handleSaveUser());
    }

    private void handleSaveUser() {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        String cpf = cpfField.getText();
        String email = emailField.getText();
        UserProfileEnum selectedRole = (UserProfileEnum) roleComboBox.getSelectedItem();

        try {
            userController.saveUser(login, password, cpf, email, selectedRole);

            JOptionPane.showMessageDialog(this, "Usuário salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            refreshUserList();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshUserList() {
        List<Users> users = userController.listAllUsers();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-5s | %-20s | %-15s | %-25s\n", "ID", "Login", "Perfil", "Email"));
        sb.append("------------------------------------------------------------------------\n");

        for (Users user : users) {
            sb.append(String.format("%-5d | %-20s | %-15s | %-25s\n",
                    user.getId(),
                    user.getLogin(),
                    user.getRole().name(),
                    user.getEmail()));
        }

        userListArea.setText(sb.toString());
    }

    private void clearFields() {
        loginField.setText("");
        passwordField.setText("");
        emailField.setText("");
        roleComboBox.setSelectedIndex(0);
    }
}