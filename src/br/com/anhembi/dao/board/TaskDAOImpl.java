package br.com.anhembi.dao.board;

import br.com.anhembi.db.DBConnection;
import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.task.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskDAOImpl implements TaskDAO {

    private static final Logger LOGGER = Logger.getLogger(TaskDAOImpl.class.getName());

    @Override
    public void save(Task task) {
        String sql = "INSERT INTO tasks (id, title, description, status, task_points, demand_id, assigned_developer_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, task.getId());
            statement.setString(2, task.getTitle());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getStatus().name());
            statement.setInt(5, task.getTaskPoints());
            statement.setObject(6, task.getDemand().getId());

            if (task.getAssignedDeveloper() != null) {
                statement.setLong(7, task.getAssignedDeveloper().getId());
            } else {
                statement.setNull(7, Types.BIGINT);
            }

            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Task salva com sucesso: " + task.getTitle());
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar a task: " + task.getTitle(), error);
        }
    }

    @Override
    public void update(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, status = ?, task_points = ?, assigned_developer_id = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getStatus().name());
            statement.setInt(4, task.getTaskPoints());

            if (task.getAssignedDeveloper() != null) {
                statement.setLong(5, task.getAssignedDeveloper().getId());
            } else {
                statement.setNull(5, Types.BIGINT);
            }

            statement.setObject(6, task.getId());

            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Task atualizada com sucesso: " + task.getTitle());
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar a task: " + task.getTitle(), error);
        }
    }

    @Override
    public Optional<Task> findById(UUID id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRowToTask(resultSet));
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar task com ID: " + id, error);
        }
        return Optional.empty();
    }

    @Override
    public List<Task> findAll() {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                taskList.add(mapRowToTask(resultSet));
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar todas as tasks", error);
        }
        return taskList;
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Task com ID: " + id + " deletada com sucesso.");
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao deletar task com ID: " + id, error);
        }
    }

    @Override
    public List<Task> findAllByDemandId(UUID demandId) {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE demand_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, demandId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    taskList.add(mapRowToTask(resultSet));
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar tasks da demanda com ID: " + demandId, error);
        }
        return taskList;
    }

    @Override
    public List<Task> findAllByAssignedDeveloper(Long developerId) {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE assigned_developer_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, developerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    taskList.add(mapRowToTask(resultSet));
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar tasks do desenvolvedor com ID: " + developerId, error);
        }
        return taskList;
    }

    @Override
    public List<Task> findAllByScrumStatus(ScrumStatusEnum status) {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE status = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    taskList.add(mapRowToTask(resultSet));
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar tasks com status: " + status, error);
        }
        return taskList;
    }

    @Override
    public List<Task> findByTitle(String title) {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE title ILIKE ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + title + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    taskList.add(mapRowToTask(resultSet));
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar task com título: " + title, error);
        }
        return taskList;
    }

    private Task mapRowToTask(ResultSet resultSet) throws SQLException {
        Task task = new Task(
                resultSet.getObject("id", UUID.class),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getInt("task_points")
        );
        task.setStatus(ScrumStatusEnum.valueOf(resultSet.getString("status")));

        return task;
    }
}
