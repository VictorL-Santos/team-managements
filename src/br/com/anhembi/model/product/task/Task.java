package br.com.anhembi.model.product.task;

public class Task {
    private String title;
    private String description;
    private String assignedDeveloperCpf;
    private final Integer taskPoints;
    private final String demandId;

    public Task(String title, String description, String assignedDeveloperCpf, String demandId, Integer taskPoints) {
        this.title = title;
        this.description = description;
        this.assignedDeveloperCpf = assignedDeveloperCpf;
        this.demandId = demandId;
        this.taskPoints = taskPoints;
    }

    // -- GETTER PARA TODOS OS CAMPOS -- \\
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getAssignedDeveloperCpf() {
        return assignedDeveloperCpf;
    }
    public  Integer getTaskPoints() {
        return taskPoints;
    }
    public String getDemandId() {
        return demandId;
    }

    // -- SETTER PARA CAMPOS MUTÁVEIS -- \\
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
    public void setAssignedDeveloperCpf(String newAssignedDeveloperCpf) {
        this.assignedDeveloperCpf = newAssignedDeveloperCpf;
    }
}
