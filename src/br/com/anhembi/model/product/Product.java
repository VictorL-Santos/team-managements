package br.com.anhembi.model.product;

import br.com.anhembi.model.Board;
import br.com.anhembi.model.product.demand.Demand;
import br.com.anhembi.model.product_owner.ProductOwner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Product extends Board {
    private ProductOwner productOwner;
    private List<Demand> demandList;
    private final Long productOwnerId;

    public Product(String title, String description, Long productOwnerId) {
        super(title, description);
        this.productOwner = null;
        this.demandList = new ArrayList<>();
        this.productOwnerId = productOwnerId;
    }

    public Product(UUID id, String title, String description, Long productOwnerId) {
        super(id, title, description);
        this.demandList = new ArrayList<>();
        this.productOwnerId = productOwnerId;
    }

    // Getters
    public ProductOwner getProductOwner() {
        return productOwner;
    }

    public List<Demand> getDemandList() {
        return demandList;
    }

    public Long getProductOwnerId() {
        return productOwnerId;
    }

    // Setters
    public void setProductOwner(ProductOwner newProductOwner) {
        this.productOwner = newProductOwner;
    }

    public void setDemandList(List<Demand> newDemandList) {
        this.demandList = newDemandList;
    }


    // Métodos \\

    /**
     * Adiciona uma demanda à lista de demandas do produto.
     * Este operacao ocorre somente na memória local
     *
     * @param demandToAdd A demanda a ser adicionada.
     */
    public boolean createDemand(Demand demandToAdd) {
        if (demandList.contains(demandToAdd)) return false;

        return this.demandList.add(demandToAdd);
    }

    /**
     * Deleta uma demanda da lista de demandas do produto.
     * Deleta todas as tasks relacionadas a essa demanda.
     * Este operacao ocorre somente na memória local
     *
     * @param demandToDelete A demanda a ser deletada.
     */
    public boolean deleteDemand(Demand demandToDelete) {
        if (this.demandList.contains(demandToDelete)) return this.demandList.remove(demandToDelete);

        return false;
    }
}
