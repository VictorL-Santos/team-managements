package br.com.anhembi.controller.board;

import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.task.Task;
import br.com.anhembi.service.TaskService;

import java.util.List;
import java.util.UUID;

/**
 * Controller para a entidade Task.
 * Delega as solicitações da View para o TaskService.
 */
public class TaskController {

    private final TaskService taskService = new TaskService();

    public Task createTask(String title, String description, int taskPoints, UUID demandId) throws Exception {
        return taskService.createNewTask(title, description, taskPoints, demandId, null);
    }

    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    public void assignDeveloper(UUID taskId, Long developerId) throws Exception {
        taskService.assignTaskToDeveloper(taskId, developerId);
    }

    public void changeStatus(UUID taskId, ScrumStatusEnum newStatus) throws Exception {
        taskService.changeTaskStatus(taskId, newStatus);
    }

    public void deleteTask(UUID id) {
        taskService.deleteTask(id);
    }
}