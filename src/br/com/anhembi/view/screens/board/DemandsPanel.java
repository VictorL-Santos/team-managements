package br.com.anhembi.view.screens.board;

import br.com.anhembi.controller.board.DemandController;
import br.com.anhembi.controller.board.ProductController;
import br.com.anhembi.controller.user.UserController;
import br.com.anhembi.model.enums.UserProfileEnum;
import br.com.anhembi.model.product.Product;
import br.com.anhembi.model.product.demand.Demand;
import br.com.anhembi.model.tech_lead.TechLead;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class DemandsPanel extends JPanel {

    // --- Componentes da Interface ---
    private JTextArea demandListArea;
    private JButton createDemandButton;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<ProductItem> productComboBox;
    private JComboBox<TechLeadItem> techLeadComboBox;

    // --- Controllers ---
    private final UserController userController;
    private final DemandController demandController;
    private final ProductController productController;

    public DemandsPanel() {
        this.userController = new UserController();
        this.demandController = new DemandController();
        this.productController = new ProductController();
        initComponents();
        addListeners();
        loadInitialData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // --- Painel de Listagem (Centro) ---
        demandListArea = new JTextArea();
        demandListArea.setEditable(false);
        demandListArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(demandListArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Demandas Cadastradas"));

        // --- Painel de Criação (Leste) ---
        JPanel creationPanel = new JPanel();
        creationPanel.setLayout(new BoxLayout(creationPanel, BoxLayout.Y_AXIS));
        creationPanel.setBorder(BorderFactory.createTitledBorder("Nova Demanda"));

        titleField = new JTextField(20);
        descriptionArea = new JTextArea(5, 20);
        productComboBox = new JComboBox<>();
        techLeadComboBox = new JComboBox<>();

        creationPanel.add(new JLabel("Título:"));
        creationPanel.add(titleField);
        creationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        creationPanel.add(new JLabel("Descrição:"));
        creationPanel.add(new JScrollPane(descriptionArea));
        creationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        creationPanel.add(new JLabel("Produto Associado:"));
        creationPanel.add(productComboBox);
        creationPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        creationPanel.add(new JLabel("Tech Lead Associado:"));
        creationPanel.add(techLeadComboBox);
        creationPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        createDemandButton = new JButton("Criar Demanda");
        creationPanel.add(createDemandButton);

        add(scrollPane, BorderLayout.CENTER);
        add(creationPanel, BorderLayout.EAST);
    }

    private void addListeners() {
        createDemandButton.addActionListener(e -> handleCreateDemand());
    }

    public void refreshPanelData() {
        // Simplesmente chama os mesmos métodos que carregam os dados
        loadProducts();
        loadTechLeads();
        refreshDemandList();
    }

    private void loadInitialData() {
        refreshPanelData();
    }

    private void refreshDemandList() {
        // Por enquanto, vamos listar todas as demandas. Uma melhoria seria filtrar por produto.
        List<Demand> demands = demandController.listAllDemands();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-38s | %-35s | %-35s | %-20s | %-15s \n", "ID", "Título", "ID Produto", "ID TechLead", "Status"));
        sb.append("-".repeat(160)).append("\n");

        for (Demand demand : demands) {
            UUID productName = demand.getProductId();
            Long techLeadName = demand.getTechLeadId();

            sb.append(String.format("%-38s | %-35s | %-35s | %-20s | %-15s \n",
                    demand.getId(),
                    demand.getTitle(),
                    productName,
                    techLeadName,
                    demand.getStatus().toString()));
        }
        demandListArea.setText(sb.toString());
    }

    private void loadProducts() {
        List<Product> products = productController.listAllProducts();
        Vector<ProductItem> productItems = new Vector<>();
        for (Product product : products) {
            productItems.add(new ProductItem(product));
        }
        productComboBox.setModel(new DefaultComboBoxModel<>(productItems));
    }

    private void loadTechLeads() {
        List<TechLead> techleads = userController.getUsersByRole(UserProfileEnum.TECH_LEAD).stream().filter(user -> user instanceof TechLead).map(user -> (TechLead) user).toList();
        Vector<TechLeadItem> techLeadItems = new Vector<>();
        for (TechLead techlead : techleads) {
            techLeadItems.add(new TechLeadItem(techlead));
        }
        techLeadComboBox.setModel(new DefaultComboBoxModel<>(techLeadItems));
    }

    private void handleCreateDemand() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        ProductItem selectedProductItem = (ProductItem) productComboBox.getSelectedItem();
        TechLeadItem selectedTechLeadItem = (TechLeadItem) techLeadComboBox.getSelectedItem();

        if (title.trim().isEmpty() || selectedProductItem == null || selectedTechLeadItem == null) {
            JOptionPane.showMessageDialog(this, "Título, Produto e TechLead são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            demandController.createDemand(title, description, selectedTechLeadItem.getId(), selectedProductItem.getId());
            JOptionPane.showMessageDialog(this, "Demanda criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            refreshDemandList();
            // Limpar campos
            titleField.setText("");
            descriptionArea.setText("");
            productComboBox.setSelectedIndex(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao criar demanda: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class ProductItem {
        private final Product product;

        public ProductItem(Product product) {
            this.product = product;
        }

        public UUID getId() {
            return product.getId();
        }

        @Override
        public String toString() {
            return product.getTitle();
        }
    }

    private static class TechLeadItem {
        private final TechLead techLead;

        public TechLeadItem(TechLead techLead) {
            this.techLead = techLead;
        }

        public Long getId() {
            return techLead.getId();
        }

        @Override
        public String toString() {
            return techLead.getLogin();
        }
    }
}
