package br.com.anhembi.dao.board;

import br.com.anhembi.db.DBConnection;
import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.demand.Demand;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DemandDAOImpl implements DemandDAO {

    private static final Logger LOGGER = Logger.getLogger(DemandDAOImpl.class.getName());

    @Override
    public void save(Demand demand) {
        String sql = "INSERT INTO demands (id, title, description, status, product_id, assigned_tech_lead_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, demand.getId());
            statement.setString(2, demand.getTitle());
            statement.setString(3, demand.getDescription());
            statement.setString(4, demand.getStatus().name());
            statement.setObject(5, demand.getProduct().getId());

            if (demand.getAssignedTechLead() != null) {
                statement.setLong(6, demand.getAssignedTechLead().getId());
            } else {
                statement.setNull(6, Types.BIGINT);
            }

            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Demanda salva com sucesso: " + demand.getTitle());
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar a demanda: " + demand.getTitle(), error);
        }
    }

    @Override
    public void update(Demand demand) {
        String sql = "UPDATE demands SET title = ?, description = ?, status = ?, assigned_tech_lead_id = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, demand.getTitle());
            statement.setString(2, demand.getDescription());
            statement.setString(3, demand.getStatus().name());

            if (demand.getAssignedTechLead() != null) {
                statement.setLong(4, demand.getAssignedTechLead().getId());
            } else {
                statement.setNull(4, Types.BIGINT);
            }

            statement.setObject(5, demand.getId());

            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Demanda atualizada com sucesso: " + demand.getTitle());
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar a demanda: " + demand.getTitle(), error);
        }
    }

    @Override
    public Optional<Demand> findById(UUID id) {
        String sql = "SELECT * FROM demands WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRowToDemand(resultSet));
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar demanda com ID: " + id, error);
        }
        return Optional.empty();
    }

    @Override
    public List<Demand> findAll() {
        List<Demand> demandList = new ArrayList<>();
        String sql = "SELECT * FROM demands";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                demandList.add(mapRowToDemand(resultSet));
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar todas as demandas", error);
        }
        return demandList;
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM demands WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Demanda com ID: " + id + " deletada com sucesso.");
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao deletar demanda com ID: " + id, error);
        }
    }

    @Override
    public List<Demand> findAllByProductId(UUID productId) {
        List<Demand> demandList = new ArrayList<>();
        String sql = "SELECT * FROM demands WHERE product_id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    demandList.add(mapRowToDemand(resultSet));
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar demandas do produto com ID: " + productId, error);
        }
        return demandList;
    }

    @Override
    public List<Demand> findAllByTechLead(Long techLeadId) {
        List<Demand> demandList = new ArrayList<>();
        String sql = "SELECT * FROM demands WHERE assigned_tech_lead_id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, techLeadId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    demandList.add(mapRowToDemand(resultSet));
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar demandas do Tech Lead com ID: " + techLeadId, error);
        }
        return demandList;
    }

    @Override
    public List<Demand> findAllByScrumStatus(ScrumStatusEnum status) {
        List<Demand> demandList = new ArrayList<>();
        String sql = "SELECT * FROM demands WHERE status = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    demandList.add(mapRowToDemand(resultSet));
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar demandas com status: " + status, error);
        }
        return demandList;
    }

    @Override
    public List<Demand> findByTitle(String title) {
        List<Demand> demandList = new ArrayList<>();
        String sql = "SELECT * FROM demands WHERE title ILIKE ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + title + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    demandList.add(mapRowToDemand(resultSet));
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar demanda com título: " + title, error);
        }
        return demandList;
    }

    private Demand mapRowToDemand(ResultSet resultSet) throws SQLException {
        Demand demand = new Demand(
                resultSet.getObject("id", UUID.class),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getLong("assigned_tech_lead_id"),
                resultSet.getObject("product_id", UUID.class)
        );
        demand.setStatus(ScrumStatusEnum.valueOf(resultSet.getString("status")));

        return demand;
    }
}
