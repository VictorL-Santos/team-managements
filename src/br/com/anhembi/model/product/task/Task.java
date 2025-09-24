package br.com.anhembi.model.product.task;

import br.com.anhembi.model.Board;
import br.com.anhembi.model.developer.Developer;
import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.demand.Demand;

import java.util.UUID;

public class Task extends Board {
    private Demand demand;
    private ScrumStatusEnum status;
    private Developer assignedDeveloper;
    private final Integer taskPoints;

    public Task(String title, String description, Demand demand, Integer taskPoints) {
        super(title, description);
        this.demand = demand;
        this.taskPoints = taskPoints;
        this.status = ScrumStatusEnum.TO_DO;
        this.assignedDeveloper = null;
    }

    public Task(UUID id, String title, String description, Integer taskPoints) {
        super(id, title, description);
        this.taskPoints = taskPoints;
        this.status = ScrumStatusEnum.TO_DO;
    }

    // -- GETTERS --
    public Developer getAssignedDeveloper() {
        return assignedDeveloper;
    }
    public ScrumStatusEnum getStatus() {
        return status;
    }
    public Integer getTaskPoints() {
        return taskPoints;
    }
    public Demand getDemand() {
        return demand;
    }

    // -- SETTERS --
    public void setAssignedDeveloper(Developer newAssignedDeveloper) {
        this.assignedDeveloper = newAssignedDeveloper;
    }
    public void setStatus(ScrumStatusEnum newStatus) {
        this.status = newStatus;
    }
    public void setDemand(Demand demand) { // Adicionado setter para hidratação
        this.demand = demand;
    }
}
