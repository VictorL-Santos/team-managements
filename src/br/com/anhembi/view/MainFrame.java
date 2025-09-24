package br.com.anhembi.view;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Team Management Application");

        setSize(960, 540);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UserPanel userPanel = new UserPanel();

        this.add(userPanel);
    }
}
