package br.com.anhembi;

import br.com.anhembi.view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Cria uma instância da nossa janela principal.
                MainFrame frame = new MainFrame();

                // 2. Torna a janela visível.
                frame.setVisible(true);
            }
        });
    }
}
