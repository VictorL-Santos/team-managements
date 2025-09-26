package br.com.anhembi.dao.board;

import br.com.anhembi.db.DBConnection;
import br.com.anhembi.model.product.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAOImpl implements ProductDAO {
    private static final Logger LOGGER = Logger.getLogger(ProductDAOImpl.class.getName());

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO products (id, title, description, product_owner_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, product.getId());
            statement.setString(2, product.getTitle());
            statement.setString(3, product.getDescription());

            if (product.getProductOwner() != null) {
                statement.setLong(4, product.getProductOwner().getId());
            } else {
                statement.setNull(4, java.sql.Types.BIGINT);
            }

            statement.executeUpdate();
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o produto: " + product.getTitle(), error);
        }
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET title = ?, description = ?, product_owner_id = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getTitle());
            statement.setString(2, product.getDescription());
            statement.setObject(4, product.getId());

            if (product.getProductOwner() != null) {
                statement.setLong(3, product.getProductOwner().getId());
            } else {
                statement.setNull(3, java.sql.Types.BIGINT);
            }
            statement.executeUpdate();

        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar o produto: " + product.getTitle(), error);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "Nenhum produto encontrado com ID: " + id);
            } else {
                LOGGER.log(Level.INFO, "Produto: " + id + " deletado com sucesso!");
            }

        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao deletar o produto: " + id, error);
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();

        String sql = "SELECT * FROM products";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = mapRowToProduct(resultSet);
                    productList.add(product);
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar produtos", error);
        }

        return productList;
    }

    @Override
    public List<Product> findAllByProductOwner(Long ownerId) {
        List<Product> productList = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE product_owner_id = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, ownerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = mapRowToProduct(resultSet);
                    productList.add(product);
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar produtos do Product Owner com ID: " + ownerId, error);
        }

        return productList;
    }

    @Override
    public List<Product> findByTitle(String title) {
        List<Product> productList = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE title ILIKE ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + title + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = mapRowToProduct(resultSet);
                    productList.add(product);
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar produto pelo título: " + title, error);
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = mapRowToProduct(resultSet);
                    return Optional.of(product);
                }
            }
        } catch (SQLException error) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar produto com ID: " + id, error);
        }

        return Optional.empty();
    }

    private Product mapRowToProduct(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getObject("id", UUID.class),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getLong("product_owner_id")
        );
    }
}
