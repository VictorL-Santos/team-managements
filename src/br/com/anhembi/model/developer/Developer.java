package br.com.anhembi.model.developer;

import br.com.anhembi.model.Users;
import br.com.anhembi.model.enums.UserProfileEnum;
import br.com.anhembi.model.product.task.Task;

import java.util.ArrayList;
import java.util.List;

public class Developer extends Users {
    private String techLeadCpf;
    private List<Task> assignedTasks;

    public Developer(String login, String password, String cpf, UserProfileEnum role, String email, String techLeadCpf, List<Task> assignedTasks) {
        super(login, password, cpf, UserProfileEnum.DEVELOPER, email);

        this.techLeadCpf = techLeadCpf;
        this.assignedTasks = new ArrayList<>();
    }

    // -- GETTER -- \\
    public String getTeachLeadCpf() {
        return techLeadCpf;
    }
    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    // -- SETTER -- \\
    public void setTeachLeadCpf(String newTechLeadCpf) {
        this.techLeadCpf = newTechLeadCpf;
    }
    public void setAssignedTasks(List<Task> newAssignedTasks) {
        this.assignedTasks = newAssignedTasks;
    }
}
