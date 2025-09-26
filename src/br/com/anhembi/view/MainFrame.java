package br.com.anhembi.view;

import br.com.anhembi.view.screens.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public MainFrame() {
        setTitle("Team Management Application");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        LoginPanel loginPanel = new LoginPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);
        HomePagePanel homePagePanel = new HomePagePanel();

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(homePagePanel, "HOME");

        this.add(mainPanel);

        showScreen("LOGIN");
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }
}
