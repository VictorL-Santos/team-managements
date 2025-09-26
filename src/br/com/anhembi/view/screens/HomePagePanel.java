package br.com.anhembi.view.screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomePagePanel extends JPanel {

    public HomePagePanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(Color.RED); // Define a cor de fundo do painel
        bannerPanel.setBorder(new EmptyBorder(15, 10, 15, 10)); // Adiciona margem interna

        JLabel bannerLabel = new JLabel("SISTEMA DE GESTÃO DE PROJETOS E PESSOAS");
        bannerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        bannerLabel.setForeground(Color.WHITE); // Define a cor do texto para branco
        bannerPanel.add(bannerLabel);

        JTabbedPane tabbedPane = new JTabbedPane();


        BoardPanel boardPanel = new BoardPanel();
        UserManagementPanel userManagementPanel = new UserManagementPanel();

        tabbedPane.addTab("Board de Projetos", null, boardPanel, "Acesso ao board de produtos, demandas e tarefas");
        tabbedPane.addTab("Gerenciamento de Usuários", null, userManagementPanel, "Acesso ao gerenciamento de usuários e equipes");


        add(bannerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
}