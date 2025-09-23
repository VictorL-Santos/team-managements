package br.com.anhembi.model.product.demand;

import br.com.anhembi.model.Board;
import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.Product;
import br.com.anhembi.model.product.task.Task;
import br.com.anhembi.model.tech_lead.TechLead;

import java.util.ArrayList;
import java.util.List;

public class Demand extends Board {
    private List<Task> taskList;
    private ScrumStatusEnum status;
    private TechLead assignedTechLead;
    private final Product product;


    public Demand(String title, String description, Product product) {
        super(title, description);
        this.product = product;

        this.status = ScrumStatusEnum.TO_DO;
        this.taskList = new ArrayList<>();
        this.assignedTechLead = null;
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

    // Métodos \\
    public boolean createTask(Task taskToAdd) {
        if(this.taskList.contains(taskToAdd)) return false;

        return this.taskList.add(taskToAdd);
    }
    public boolean deleteTask(Task taskToDelete) {
        if(this.taskList.contains(taskToDelete)) return this.taskList.remove(taskToDelete);

        return false;
    }
}
