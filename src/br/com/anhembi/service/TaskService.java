package br.com.anhembi.service;

import br.com.anhembi.dao.board.DemandDAO;
import br.com.anhembi.dao.board.DemandDAOImpl;
import br.com.anhembi.dao.board.TaskDAO;
import br.com.anhembi.dao.board.TaskDAOImpl;
import br.com.anhembi.dao.user.UserDAO;
import br.com.anhembi.dao.user.UserDAOImpl;
import br.com.anhembi.model.developer.Developer;
import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.demand.Demand;
import br.com.anhembi.model.product.task.Task;

import java.util.Optional;
import java.util.UUID;

/**
 * Camada de Serviço para a entidade Task.
 */
public class TaskService {

    private final TaskDAO taskDAO = new TaskDAOImpl();
    private final DemandDAO demandDAO = new DemandDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    /**
     * Cria uma nova tarefa para uma demanda existente.
     */
    public Task createNewTask(String title, String description, int taskPoints, UUID demandId) throws Exception {
        Demand demand = demandDAO.findById(demandId)
                .orElseThrow(() -> new Exception("Demanda com ID " + demandId + " não encontrada."));

        Task newTask = new Task(title, description, demand, taskPoints);

        taskDAO.save(newTask);
        return newTask;
    }

    /**
     * Busca uma tarefa pelo ID e a retorna totalmente hidratada.
     */
    public Optional<Task> getFullTaskDetails(UUID id) {
        // O DAO já faz a hidratação do Developer e da Demand, então uma chamada simples é suficiente.
        return taskDAO.findById(id);
    }

    /**
     * Atribui uma tarefa a um desenvolvedor.
     */
    public void assignTaskToDeveloper(UUID taskId, Long developerId) throws Exception {
        Task task = taskDAO.findById(taskId)
                .orElseThrow(() -> new Exception("Tarefa com ID " + taskId + " não encontrada."));

        Developer developer = (Developer) userDAO.findById(developerId)
                .filter(user -> user instanceof Developer)
                .orElseThrow(() -> new Exception("Desenvolvedor com ID " + developerId + " não encontrado."));

        if (task.getStatus() == ScrumStatusEnum.DONE) {
            throw new Exception("Não é possível atribuir uma tarefa que já foi concluída.");
        }

        task.setAssignedDeveloper(developer);

        taskDAO.update(task);
    }

    /**
     * Altera o status de uma tarefa.
     */
    public void changeTaskStatus(UUID taskId, ScrumStatusEnum newStatus) throws Exception {
        Task task = taskDAO.findById(taskId)
                .orElseThrow(() -> new Exception("Tarefa com ID " + taskId + " não encontrada."));

        // Adicionar lógica de negócio aqui, se necessário (ex: uma task não pode voltar de DONE para TO_DO)

        task.setStatus(newStatus);

        taskDAO.update(task);
    }

    /**
     * Deleta uma tarefa pelo seu ID.
     */
    public void deleteTask(UUID id) {
        taskDAO.deleteById(id);
    }
}
