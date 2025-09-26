package br.com.anhembi.dao.board;

import br.com.anhembi.model.product.Product;

import java.util.List;
import java.util.UUID;

public interface ProductDAO extends GenericBoardDAO<Product, UUID> {
    List<Product> findAllByProductOwner(Long ownerId);

    List<Product> findByTitle(String title);
}
