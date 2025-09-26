package br.com.anhembi.view.screens.board;

import br.com.anhembi.controller.board.ProductController;
import br.com.anhembi.controller.user.UserController;
import br.com.anhembi.model.Users;
import br.com.anhembi.model.enums.UserProfileEnum;
import br.com.anhembi.model.product.Product;
import br.com.anhembi.model.product_owner.ProductOwner;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class ProductsPanel extends JPanel {

    // --- Componentes da Interface ---
    private JTextArea productListArea;
    private JButton createProductButton;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<ProductOwnerItem> ownerComboBox;

    // --- Controllers ---
    private final ProductController productController;
    private final UserController userController;

    public ProductsPanel() {
        this.productController = new ProductController();
        this.userController = new UserController();
        initComponents();
        addListeners();
        loadInitialData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // --- Painel de Listagem (Centro) ---
        productListArea = new JTextArea();
        productListArea.setEditable(false);
        productListArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(productListArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Produtos Cadastrados"));

        // --- Painel de Criação (Leste) ---
        JPanel creationPanel = new JPanel();
        creationPanel.setLayout(new BoxLayout(creationPanel, BoxLayout.Y_AXIS));
        creationPanel.setBorder(BorderFactory.createTitledBorder("Novo Produto"));

        titleField = new JTextField(20);
        descriptionArea = new JTextArea(5, 20);
        ownerComboBox = new JComboBox<>();

        creationPanel.add(new JLabel("Título:"));
        creationPanel.add(titleField);
        creationPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaçamento
        creationPanel.add(new JLabel("Descrição:"));
        creationPanel.add(new JScrollPane(descriptionArea));
        creationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        creationPanel.add(new JLabel("Product Owner:"));
        creationPanel.add(ownerComboBox);
        creationPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        createProductButton = new JButton("Criar Produto");
        creationPanel.add(createProductButton);

        add(scrollPane, BorderLayout.CENTER);
        add(creationPanel, BorderLayout.EAST);
    }

    private void addListeners() {
        createProductButton.addActionListener(e -> handleCreateProduct());
    }

    public void refreshPanelData() {
        loadProductOwners();
        refreshProductList();
    }

    private void loadInitialData() {
        refreshPanelData();
    }

    private void refreshProductList() {
        List<Product> products = productController.listAllProducts();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-38s | %-45s | %-25s\n", "ID", "Título", "Product Owner"));
        sb.append("-".repeat(143)).append("\n");

        for (Product product : products) {
            String ownerName = String.valueOf(product.getProductOwnerId());
            sb.append(String.format("%-38s | %-45s | %-25s\n",
                    product.getId(),
                    product.getTitle(),
                    ownerName));
        }
        productListArea.setText(sb.toString());
    }

    private void loadProductOwners() {
        List<Users> productOwners = userController.getUsersByRole(UserProfileEnum.PRODUCT_OWNER);

        Vector<ProductOwnerItem> ownerItems = new Vector<>();
        for (Users user : productOwners) {
            if (user instanceof ProductOwner) {
                ownerItems.add(new ProductOwnerItem((ProductOwner) user));
            }
        }
        ownerComboBox.setModel(new DefaultComboBoxModel<>(ownerItems));
    }

    private void handleCreateProduct() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        ProductOwnerItem selectedOwnerItem = (ProductOwnerItem) ownerComboBox.getSelectedItem();

        if (title.trim().isEmpty() || selectedOwnerItem == null) {
            JOptionPane.showMessageDialog(this, "Título e Product Owner são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            productController.createProduct(title, description, selectedOwnerItem.getId());
            JOptionPane.showMessageDialog(this, "Produto criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            refreshProductList();

            titleField.setText("");
            descriptionArea.setText("");
            ownerComboBox.setSelectedIndex(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao criar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class ProductOwnerItem {
        private final ProductOwner po;

        public ProductOwnerItem(ProductOwner po) {
            this.po = po;
        }

        public Long getId() {
            return po.getId();
        }

        @Override
        public String toString() {
            return po.getLogin();
        }
    }
}