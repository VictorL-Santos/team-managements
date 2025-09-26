package br.com.anhembi.view.screens.board;


import br.com.anhembi.controller.board.DemandController;
import br.com.anhembi.controller.board.TaskController;
import br.com.anhembi.controller.user.UserController;
import br.com.anhembi.model.developer.Developer;
import br.com.anhembi.model.enums.UserProfileEnum;
import br.com.anhembi.model.product.demand.Demand;
import br.com.anhembi.model.product.task.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class TasksPanel extends JPanel {

    // --- Componentes da Interface ---
    private JTextArea taskListArea;
    private JButton createTaskButton;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JSpinner pointsSpinner; // Spinner para os pontos da tarefa
    private JComboBox<DemandItem> demandComboBox;
    private JComboBox<DeveloperItem> developerComboBox;

    // --- Controllers ---
    private final TaskController taskController;
    private final DemandController demandController;
    private final UserController userController;

    public TasksPanel() {
        this.taskController = new TaskController();
        this.demandController = new DemandController();
        this.userController = new UserController();
        initComponents();
        addListeners();
        loadInitialData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // --- Painel de Listagem (Centro) ---
        taskListArea = new JTextArea();
        taskListArea.setEditable(false);
        taskListArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(taskListArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tarefas Cadastradas"));

        // --- Painel de Criação (Leste) ---
        JPanel creationPanel = new JPanel();
        creationPanel.setLayout(new BoxLayout(creationPanel, BoxLayout.Y_AXIS));
        creationPanel.setBorder(BorderFactory.createTitledBorder("Nova Tarefa"));

        titleField = new JTextField(20);
        descriptionArea = new JTextArea(5, 20);
        demandComboBox = new JComboBox<>();
        developerComboBox = new JComboBox<>();

        // Spinner para pontos, de 1 a 10, começando em 1.
        pointsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        creationPanel.add(new JLabel("Título:"));
        creationPanel.add(titleField);
        creationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        creationPanel.add(new JLabel("Descrição:"));
        creationPanel.add(new JScrollPane(descriptionArea));
        creationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        creationPanel.add(new JLabel("Demanda Associada:"));
        creationPanel.add(demandComboBox);
        creationPanel.add(new JLabel("Developer Associado:"));
        creationPanel.add(developerComboBox);
        creationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        creationPanel.add(new JLabel("Pontos da Tarefa:"));
        creationPanel.add(pointsSpinner);
        creationPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        createTaskButton = new JButton("Criar Tarefa");
        creationPanel.add(createTaskButton);

        add(scrollPane, BorderLayout.CENTER);
        add(creationPanel, BorderLayout.EAST);
    }

    private void addListeners() {
        createTaskButton.addActionListener(e -> handleCreateTask());
    }

    public void refreshPanelData() {
        loadDemands();
        loadDevelopers();
        refreshTaskList();
    }

    private void loadInitialData() {
        refreshPanelData();
    }

    private void refreshTaskList() {
        List<Task> tasks = taskController.getAllTasks();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s | %-35s | %-35s | %-20s | %-15s | %-5s\n", "ID", "Título", "Demanda", "Dev Responsável", "Status", "Pts"));
        sb.append("-".repeat(140)).append("\n");

        for (Task task : tasks) {
            String demandName = (task.getDemand() != null) ? task.getDemand().getTitle() : "N/A";
            String devName = (task.getAssignedDeveloper() != null) ? task.getAssignedDeveloper().getLogin() : "N/A";

            sb.append(String.format("%-15s | %-35s | %-35s | %-20s | %-15s | %-5s\n",
                    task.getId(),
                    task.getTitle(),
                    demandName,
                    devName,
                    task.getStatus().name(),
                    task.getTaskPoints()));
        }
        taskListArea.setText(sb.toString());
    }

    private void loadDemands() {
        List<Demand> demands = demandController.listAllDemands();
        Vector<DemandItem> demandItems = new Vector<>();
        for (Demand demand : demands) {
            demandItems.add(new DemandItem(demand));
        }
        demandComboBox.setModel(new DefaultComboBoxModel<>(demandItems));
    }

    private void loadDevelopers() {
        List<Developer> developers = userController.getUsersByRole(UserProfileEnum.DEVELOPER).stream().filter(user -> user instanceof Developer).map(user -> (Developer) user).toList();
        Vector<DeveloperItem> developerItems = new Vector<>();
        for (Developer developer : developers) {
            developerItems.add(new DeveloperItem(developer));
        }
        developerComboBox.setModel(new DefaultComboBoxModel<>(developerItems));
    }

    private void handleCreateTask() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        DemandItem selectedDemandItem = (DemandItem) demandComboBox.getSelectedItem();
        int taskPoints = (Integer) pointsSpinner.getValue();

        if (title.trim().isEmpty() || selectedDemandItem == null) {
            JOptionPane.showMessageDialog(this, "Título e Demanda são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            taskController.createTask(title, description, taskPoints, selectedDemandItem.getId());
            JOptionPane.showMessageDialog(this, "Tarefa criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            refreshTaskList();
            // Limpar campos
            titleField.setText("");
            descriptionArea.setText("");
            demandComboBox.setSelectedIndex(0);
            pointsSpinner.setValue(1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao criar tarefa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Classe interna para exibir o nome da Demanda no ComboBox
    private static class DemandItem {
        private final Demand demand;

        public DemandItem(Demand demand) {
            this.demand = demand;
        }

        public UUID getId() {
            return demand.getId();
        }

        @Override
        public String toString() {
            return demand.getTitle();
        }
    }

    private static class DeveloperItem {
        private final Developer developer;

        public DeveloperItem(Developer developer) {
            this.developer = developer;
        }

        public Long getId() {
            return developer.getId();
        }

        @Override
        public String toString() {
            return developer.getLogin();
        }
    }
}
