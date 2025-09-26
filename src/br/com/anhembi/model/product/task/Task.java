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
    private final UUID demandId;
    private final Long developerId;

    public Task(String title, String description, Demand demand, Integer taskPoints, UUID demandId, Long developerId) {
        super(title, description);
        this.demand = demand;
        this.taskPoints = taskPoints;
        this.demandId = demandId;
        this.developerId = developerId;
        this.status = ScrumStatusEnum.TO_DO;
        this.assignedDeveloper = null;
    }

    public Task(UUID id, String title, String description, Integer taskPoints, Long developerId, UUID demandId) {
        super(id, title, description);
        this.taskPoints = taskPoints;
        this.demandId = demandId;
        this.developerId = developerId;
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
    public UUID getDemandId() {
        return demandId;
    }
    public Long getDeveloperId() {
        return developerId;
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
