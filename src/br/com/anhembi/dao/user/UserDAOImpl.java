package br.com.anhembi.dao.user;

import br.com.anhembi.db.DBConnection;
import br.com.anhembi.model.Users;
import br.com.anhembi.model.developer.Developer;
import br.com.anhembi.model.enums.UserProfileEnum;
import br.com.anhembi.model.product_owner.ProductOwner;
import br.com.anhembi.model.tech_lead.TechLead;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class.getName());

    @Override
    public void save(Users user) {
        final String sql;

        if (user.getId() == null) {
            sql = "INSERT INTO users (login, password_hash, cpf_hash, role, email) VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE users SET login = ?, email = ? WHERE id = ?";
        }

        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                if (user.getId() == null) {
                    try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        statement.setString(1, user.getLogin());
                        statement.setString(2, user.getPassword());
                        statement.setString(3, user.getCpf());
                        statement.setString(4, user.getRole().name());
                        statement.setString(5, user.getEmail());

                        int affectedRows = statement.executeUpdate();

                        if (affectedRows > 0) {
                            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                                if (generatedKeys.next()) user.setId(generatedKeys.getLong(1));
                            }
                        }
                    }
                } else {
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, user.getLogin());
                        statement.setString(2, user.getEmail());
                        statement.setLong(3, user.getId());

                        statement.executeUpdate();
                    }
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro de persistência ao salvar o usuário: " + user.getLogin(), error);
        }
    }

    @Override
    public Optional<Users> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String roleString = resultSet.getString("role");
                    UserProfileEnum role = UserProfileEnum.valueOf(roleString);
                    Users user = CreateUserObject(resultSet, role);
                    return Optional.ofNullable(user);
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar usuário com ID: " + id, error);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Users> findByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String roleString = resultSet.getString("role");
                    UserProfileEnum role = UserProfileEnum.valueOf(roleString);
                    Users user = CreateUserObject(resultSet, role);
                    return Optional.ofNullable(user);
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar usuário com login: " + login, error);
        }
        return Optional.empty();
    }

    @Override
    public List<Users> findAllByRole(UserProfileEnum role) {
        List<Users> userList = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE role = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role.name());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UserProfileEnum userRole = UserProfileEnum.valueOf(resultSet.getString("role"));
                    Users user = CreateUserObject(resultSet, userRole);
                    if (user != null) userList.add(user);
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar usuários com role: " + role, error);
        }

        return userList;
    }

    @Override
    public List<Users> getAllUsers() {
        List<Users> userList = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UserProfileEnum userRole = UserProfileEnum.valueOf(resultSet.getString("role"));
                    Users user = CreateUserObject(resultSet, userRole);
                    if (user != null) userList.add(user);
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar usuários", error);
        }

        return userList;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "Nenhum usuário encontrado com ID: " + id);
            } else {
                LOGGER.info("Usuário com ID: " + id + " deletado com sucesso");
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao deletar usuário com ID: " + id, error);
        }
    }

    @Override
    public void updateManagerId(Long userId, Long managerId) {
        String sql = "UPDATE users SET manager_id = ? WHERE id = ? AND manager_id IS NULL";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, managerId);
            statement.setLong(2, userId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "Nenhum desenvolvedor encontrado com ID: " + userId);
            } else {
                LOGGER.log(Level.INFO, "Desenvolvedor: " + userId + " salvo na equipe do techLead: " + managerId);
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar desenvolvedor: " + userId + " na equipe do techLead: " + managerId, error);
        }
    }

    @Override
    public void clearManagerId(Long userId, Long managerId) {
        String sql = "UPDATE users SET manager_id = NULL WHERE id = ? AND manager_id = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setLong(2, managerId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "Nenhum desenvolvedor encontrado com ID: " + userId);
            } else {
                LOGGER.log(Level.INFO, "Desenvolvedor: " + userId + " removido da equipe do techLead: " + managerId);
            }

        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao remover desenvolvedor: " + userId + " da equipe do techLead: " + managerId, error);
        }
    }

    @Override
    public List<Users> findAllByTechLeadId(Long id) {
        List<Users> userList = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE manager_id = ?";

        try(Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UserProfileEnum userRole = UserProfileEnum.valueOf(resultSet.getString("role"));
                    Users user = CreateUserObject(resultSet, userRole);
                    if (user != null) userList.add(user);
                }
            }
        }
        catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar equipe de desenvolvedores do techLead com ID: " + id, error);
        }

        return userList;
    }

    @Override
    public void updateProductOwnerID(Long id, Long productOwnerId) {
        String sql = "UPDATE users SET product_owner_id = ? WHERE id = ? AND product_owner_id IS NULL";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, productOwnerId);
            statement.setLong(2, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "Nenhum TechLead encontrado com ID: " + id);
            } else {
                LOGGER.log(Level.INFO, "TechLead: " + id + " salvo na equipe do PO: " + productOwnerId);
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar techLead: " + id + " na equipe do PO: " + productOwnerId, error);
        }
    }

    @Override
    public void clearProductOwnerID(Long id, Long productOwnerId) {
        String sql = "UPDATE users SET product_owner_id = NULL WHERE id = ? AND product_owner_id = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setLong(2, productOwnerId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "Nenhum Tech Lead encontrado com ID: " + id);
            } else {
                LOGGER.log(Level.INFO, "Tech Lead: " + id + " removido da equipe do PO: " + productOwnerId);
            }

        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao remover Tech Lead: " + id + " da equipe do PO: " + productOwnerId, error);
        }
    }

    @Override
    public List<Users> findAllByProductOwnerId(Long id) {
        List<Users> userList = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE product_owner_id = ?";

        try(Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UserProfileEnum userRole = UserProfileEnum.valueOf(resultSet.getString("role"));
                    Users user = CreateUserObject(resultSet, userRole);
                    if (user != null) userList.add(user);
                }
            }
        }
        catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar equipe de techLeads do ProductOwner com ID: " + id, error);
        }

        return userList;
    }

    private Users CreateUserObject(ResultSet resultSet, UserProfileEnum userRole) throws SQLException {
        return switch (userRole) {
            case DEVELOPER -> new Developer(
                    resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password_hash"),
                    resultSet.getString("cpf_hash"),
                    resultSet.getString("email")
            );
            case TECH_LEAD -> new TechLead(
                    resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password_hash"),
                    resultSet.getString("cpf_hash"),
                    resultSet.getString("email")
            );
            case PRODUCT_OWNER -> new ProductOwner(
                    resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password_hash"),
                    resultSet.getString("cpf_hash"),
                    resultSet.getString("email")
            );
            default -> null;
        };
    }
}
