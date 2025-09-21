package br.com.anhembi.model.tech_lead;

import br.com.anhembi.model.Users;
import br.com.anhembi.model.developer.Developer;
import br.com.anhembi.model.product.Product;

import java.util.List;

public class TechLead extends Users {
    private List<Developer> managedTeam;
    private List<Product> managedProducts;

    public TechLead(String login, String password, String cpf, String email, List<Developer> managedTeam, List<Product> managedProducts ) {
        super(login, password, cpf, br.com.anhembi.model.enums.UserProfileEnum.TECH_LEAD, email);

        this.managedTeam = managedTeam;
        this.managedProducts = managedProducts;
    }

    // -- GETTER -- \\
    public String getManagedTeam() {
        return managedTeam.toString();
    }
    public String getManagedProducts() {
        return managedProducts.toString();
    }

    // -- SETTER -- \\
    public void setManagedTeam(List<Developer> newManagedTeam) {
        this.managedTeam = newManagedTeam;
    }
    public void setManagedProducts(List<Product> newManagedProducts) {
        this.managedProducts = newManagedProducts;
    }

}
