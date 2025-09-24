package br.com.anhembi.service;

import br.com.anhembi.dao.board.DemandDAO;
import br.com.anhembi.dao.board.DemandDAOImpl;
import br.com.anhembi.dao.board.ProductDAO;
import br.com.anhembi.dao.board.ProductDAOImpl;
import br.com.anhembi.dao.user.UserDAO;
import br.com.anhembi.dao.user.UserDAOImpl;
import br.com.anhembi.model.product.Product;
import br.com.anhembi.model.product_owner.ProductOwner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Camada de Serviço para a entidade Product.
 * Contém a lógica de negócio e orquestra as chamadas aos DAOs.
 */
public class ProductService {

    private final ProductDAO productDAO = new ProductDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final DemandDAO demandDAO = new DemandDAOImpl();

    /**
     * Cria um novo produto e o associa a um Product Owner.
     */
    public Product createNewProduct(String title, String description, Long ownerId) throws Exception {
        if (title == null || title.trim().isEmpty()) {
            throw new Exception("O título do produto não pode ser vazio.");
        }

        ProductOwner owner = (ProductOwner) userDAO.findById(ownerId)
                .filter(user -> user instanceof ProductOwner)
                .orElseThrow(() -> new Exception("Product Owner com ID " + ownerId + " não encontrado."));

        Product newProduct = new Product(title, description);
        newProduct.setProductOwner(owner);

        productDAO.save(newProduct);
        return newProduct;
    }

    /**
     * Busca um produto pelo seu ID e o retorna totalmente hidratado.
     */
    public Optional<Product> getFullProductDetails(UUID id) {
        Optional<Product> optionalProduct = productDAO.findById(id);

        optionalProduct.ifPresent(product -> {
            product.setDemandList(demandDAO.findAllByProductId(product.getId()));
        });

        return optionalProduct;
    }

    /**
     * Busca todos os produtos (de forma "rasa", sem hidratação profunda).
     */
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Busca produtos pelo título (busca parcial, case-insensitive).
     */
    public List<Product> searchProductsByTitle(String keyword) {
        return productDAO.findByTitle(keyword);
    }

    /**
     * Atualiza as informações de um produto.
     */
    public void updateProduct(UUID productId, String newTitle, String newDescription) throws Exception {
        if (newTitle == null || newTitle.trim().isEmpty()) {
            throw new Exception("O título do produto não pode ser vazio.");
        }

        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new Exception("Produto com ID " + productId + " não encontrado."));

        product.setTitle(newTitle);
        product.setDescription(newDescription);

        productDAO.update(product);
    }

    /**
     * Deleta um produto pelo seu ID.
     */
    public void deleteProduct(UUID id) {
        productDAO.deleteById(id);
    }
}
