package br.com.anhembi.view.screens;

import br.com.anhembi.view.screens.board.DemandsPanel;
import br.com.anhembi.view.screens.board.ProductsPanel;
import br.com.anhembi.view.screens.board.TasksPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class BoardPanel extends JPanel {
    public BoardPanel() {
        initComponents();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        ProductsPanel productsPanel = new ProductsPanel();
        DemandsPanel demandsPanel = new DemandsPanel();
        TasksPanel tasksPanel = new TasksPanel();

        tabbedPane.addTab("Produtos", null, productsPanel, "Visualizar e gerenciar produtos");
        tabbedPane.addTab("Demandas", null, demandsPanel, "Visualizar e gerenciar demandas");
        tabbedPane.addTab("Tarefas", null, tasksPanel, "Visualizar e gerenciar tarefas");

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Component selectedComponent = tabbedPane.getSelectedComponent();

                if (selectedComponent == productsPanel) {
                    productsPanel.refreshPanelData();
                } else if (selectedComponent == demandsPanel) {
                    demandsPanel.refreshPanelData();
                } else if (selectedComponent == tasksPanel) {
                    tasksPanel.refreshPanelData();
                }
            }
        });

        add(tabbedPane, BorderLayout.CENTER);
    }
}
