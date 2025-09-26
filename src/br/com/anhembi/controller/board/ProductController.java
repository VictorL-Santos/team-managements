package br.com.anhembi.controller.board;

import br.com.anhembi.model.product.Product;
import br.com.anhembi.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller para a entidade Product.
 * Delega as solicitações da View para o ProductService.
 */
public class ProductController {
    private final ProductService productService = new ProductService();

    public void createProduct(String title, String description, Long ownerId) throws Exception {
        productService.createNewProduct(title, description, ownerId);
    }

    public Optional<Product> getProductDetails(UUID id) {
        return productService.getFullProductDetails(id);
    }

    public List<Product> listAllProducts() {
        return productService.getAllProducts();
    }

    public List<Product> searchProducts(String keyword) {
        return productService.searchProductsByTitle(keyword);
    }

    public void updateProduct(UUID productId, String newTitle, String newDescription) throws Exception {
        productService.updateProduct(productId, newTitle, newDescription);
    }

    public void deleteProduct(UUID id) {
        productService.deleteProduct(id);
    }
}