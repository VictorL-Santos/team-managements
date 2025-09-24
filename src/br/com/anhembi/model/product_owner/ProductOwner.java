package br.com.anhembi.model.product_owner;

import br.com.anhembi.model.Users;
import br.com.anhembi.model.enums.UserProfileEnum;
import br.com.anhembi.model.product.Product;
import br.com.anhembi.model.tech_lead.TechLead;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductOwner extends Users {
    private List<TechLead> managedTechLeads;
    private List<Product> ownedProducts;

    public ProductOwner(Long id, String login, String password, String cpf, String email) {
        super(id, login, password, cpf, UserProfileEnum.PRODUCT_OWNER, email);

        this.managedTechLeads = new ArrayList<>();
        this.ownedProducts = new ArrayList<>();
    }

    public ProductOwner(String login, String password, String cpf, String email) {
        super(null, login, password, cpf, UserProfileEnum.PRODUCT_OWNER, email);

        this.managedTechLeads = new ArrayList<>();
        this.ownedProducts = new ArrayList<>();
    }

    // GETTER \\
    public List<TechLead> getManagedTechLeads() {
        return managedTechLeads;
    }

    public List<Product> getOwnedProducts() {
        return ownedProducts;
    }

    // SETTER \\
    public void setManagedTechLeads(List<TechLead> newManagedTechLeads) {
        this.managedTechLeads = newManagedTechLeads;
    }

    public void setOwnedProducts(List<Product> newOwnedProducts) {
        this.ownedProducts = newOwnedProducts;
    }

    // MÉTODOS \\
    public boolean addProductToOwnedList(Product productToAdd) {
        if (this.ownedProducts.contains(productToAdd)) return false;

        return this.ownedProducts.add(productToAdd);
    }

    public boolean removeProductFromOwnedList(Product productToRemove) {
        if (this.ownedProducts.contains(productToRemove)) return this.ownedProducts.remove(productToRemove);

        return false;
    }

    public boolean addTechLeadToTeam(TechLead techLeadToAdd) {
        if (this.managedTechLeads.contains(techLeadToAdd)) return false;

        return this.managedTechLeads.add(techLeadToAdd);
    }

    public boolean removeTechLeadFromTeam(TechLead techLeadToRemove) {
        if (this.managedTechLeads.contains(techLeadToRemove)) return this.managedTechLeads.remove(techLeadToRemove);

        return false;
    }
}
