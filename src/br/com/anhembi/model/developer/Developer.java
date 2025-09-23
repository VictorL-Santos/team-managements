package br.com.anhembi.model.developer;

import br.com.anhembi.model.Users;
import br.com.anhembi.model.enums.UserProfileEnum;
import br.com.anhembi.model.product.task.Task;
import br.com.anhembi.model.tech_lead.TechLead;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Developer extends Users {
    private TechLead techLead;
    private List<Task> assignedTasks;

    public Developer(Long id, String login, String password, String cpf, UserProfileEnum role, String email) {
        super(id, login, password, cpf, UserProfileEnum.DEVELOPER, email);

        this.techLead = null;
        this.assignedTasks = new ArrayList<>();
    }

    public Developer(String login, String password, String cpf, UserProfileEnum role, String email) {
        super(null, login, password, cpf, UserProfileEnum.DEVELOPER, email);

        this.techLead = null;
        this.assignedTasks = new ArrayList<>();
    }

    // -- GETTER -- \\
    public TechLead getTechLead() {
        return techLead;
    }
    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    // -- SETTER -- \\
    public void setAssignedTasks(List<Task> newAssignedTasks) {
        this.assignedTasks = newAssignedTasks;
    }
}
