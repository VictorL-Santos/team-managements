package br.com.anhembi.view;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton saveButton;
    private JTextArea userListArea;

    public UserPanel() {
        setLayout(new BorderLayout(48, 64));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 5, 5));

        formPanel.add(new JLabel("Login:"));
        loginField = new JTextField(20); // O '20' sugere um tamanho preferido
        formPanel.add(loginField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        saveButton = new JButton("Salvar Usuário");
        formPanel.add(new JLabel());
        formPanel.add(saveButton);

        userListArea = new JTextArea();
        userListArea.setEditable(false); // Impede que o usuário digite na área de listagem

        // Adiciona a área de texto a um JScrollPane para ter barras de rolagem se o texto for grande
        JScrollPane scrollPane = new JScrollPane(userListArea);

        // --- 4. Adiciona os painéis criados ao painel principal (UserPanel) ---
        add(formPanel, BorderLayout.NORTH); // O formulário fica na parte de cima
        add(scrollPane, BorderLayout.CENTER);
    }
}
