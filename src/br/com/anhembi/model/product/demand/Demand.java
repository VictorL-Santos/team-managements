package br.com.anhembi.model.product.demand;

import br.com.anhembi.model.Board;
import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.Product;
import br.com.anhembi.model.product.task.Task;
import br.com.anhembi.model.tech_lead.TechLead;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Demand extends Board {
    private List<Task> taskList;
    private ScrumStatusEnum status;
    private TechLead assignedTechLead;
    private Product product;
    private final Long techLeadId;
    private final UUID productId;

    public Demand(String title, String description, Product product, Long techLeadId, UUID productId) {
        super(title, description);
        this.techLeadId = techLeadId;
        this.productId = productId;
        this.product = product;
        this.status = ScrumStatusEnum.TO_DO;
        this.taskList = new ArrayList<>();
        this.assignedTechLead = null;
    }

    /**
     * Construtor para recriar um objeto Demand a partir do banco de dados.
     */
    public Demand(UUID id, String title, String description, Long techLeadId, UUID productId) {
        super(id, title, description);
        this.techLeadId = techLeadId;
        this.productId = productId;
        this.taskList = new ArrayList<>();
        this.status = ScrumStatusEnum.TO_DO;
    }

    // Getters
    public ScrumStatusEnum getStatus() {
        return status;
    }
    public TechLead getAssignedTechLead() {
        return assignedTechLead;
    }
    public List<Task> getTaskList() {
        return taskList;
    }
    public Product getProduct() {
        return product;
    }
    public Long getTechLeadId() {
        return techLeadId;
    }
    public UUID getProductId() {
        return productId;
    }

    // Setters
    public void setStatus(ScrumStatusEnum status) {
        this.status = status;
    }
    public void setAssignedTechLead(TechLead newAssignedTechLead) {
        this.assignedTechLead = newAssignedTechLead;
    }
    public void setTaskList(List<Task> newTaskList) {
        this.taskList = newTaskList;
    }
    public void setProduct(Product product) { // Adicionado setter para hidratação
        this.product = product;
    }

    // Métodos
    public boolean createTask(Task taskToAdd) {
        if(this.taskList.contains(taskToAdd)) return false;
        return this.taskList.add(taskToAdd);
    }
    public boolean deleteTask(Task taskToDelete) {
        if(this.taskList.contains(taskToDelete)) return this.taskList.remove(taskToDelete);
        return false;
    }
}
