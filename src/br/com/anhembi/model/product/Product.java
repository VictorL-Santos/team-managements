package br.com.anhembi.model.product;

import br.com.anhembi.model.product.demand.Demand;

import java.util.List;

public class Product {
    private String name;
    private String description;
    private String productOwnerCpf;
    private List<Demand> demandList;

    public Product(String name, String description, String productOwnerCpf) {
        this.name = name;
        this.description = description;
        this.productOwnerCpf = productOwnerCpf;
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getProductOwnerCpf() {
        return productOwnerCpf;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setProductOwnerCpf(String productOwnerCpf) {
        this.productOwnerCpf = productOwnerCpf;
    }
}
