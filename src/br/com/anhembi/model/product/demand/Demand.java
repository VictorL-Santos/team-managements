package br.com.anhembi.model.product.demand;

import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.task.Task;

import java.util.List;

public class Demand {
    private String title;
    private ScrumStatusEnum status;
    private String assignedTechLeadCpf;
    private List<Task> taskList;
    private final String productId;


    public Demand(String title, ScrumStatusEnum status, String assignedTechLeadCpf, String productId) {
        this.title = title;
        this.status = status;
        this.assignedTechLeadCpf = assignedTechLeadCpf;
        this.productId = productId;
    }

    // Getters
    public String getTitle() {
        return title;
    }
    public ScrumStatusEnum getStatus() {
        return status;
    }
    public String getAssignedDeveloperCpf() {
        return assignedTechLeadCpf;
    }
    public String getProductId() {
        return productId;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setStatus(ScrumStatusEnum status) {
        this.status = status;
    }
    public void setAssignedTechLeadCpf(String assignedTechLeadCpf) {
        this.assignedTechLeadCpf = assignedTechLeadCpf;
    }
}
