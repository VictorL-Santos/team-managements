package br.com.anhembi.model.tech_lead;

import br.com.anhembi.model.Users;
import br.com.anhembi.model.developer.Developer;
import br.com.anhembi.model.enums.UserProfileEnum;
import br.com.anhembi.model.product.demand.Demand;
import br.com.anhembi.model.product_owner.ProductOwner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TechLead extends Users {
    private ProductOwner productOwner;
    private List<Developer> managedTeam;
    private List<Demand> managedDemands;

    public TechLead(Long id, String login, String password, String cpf, String email) {
        super(id, login, password, cpf, UserProfileEnum.TECH_LEAD, email);

        this.productOwner = null;
        this.managedTeam = new ArrayList<>();
        this.managedDemands = new ArrayList<>();
    }

    public TechLead(String login, String password, String cpf, String email) {
        super(null, login, password, cpf, br.com.anhembi.model.enums.UserProfileEnum.TECH_LEAD, email);

        this.productOwner = null;
        this.managedTeam = new ArrayList<>();
        this.managedDemands = new ArrayList<>();
    }

    // -- GETTER -- \\
    public List<Developer> getManagedTeam() {
        return managedTeam;
    }

    public List<Demand> getManagedDemands() {
        return managedDemands;
    }

    public ProductOwner getProductOwner() {
        return productOwner;
    }

    // -- SETTER -- \\
    public void setManagedTeam(List<Developer> newManagedTeam) {
        this.managedTeam = newManagedTeam;
    }

    public void setManagedDemands(List<Demand> newManagedDemands) {
        this.managedDemands = newManagedDemands;
    }

    public void setProductOwner(ProductOwner newProductOwner) {
        this.productOwner = newProductOwner;
    }


    // -- MÉTODOS -- \\

    /**
     * Adiciona um único desenvolvedor à equipe gerenciada.
     * Esta operação ocorre apenas na memória.
     *
     * @param developerToAdd O objeto Developer a ser adicionado.
     */
    public boolean addDeveloperToTeam(Developer developerToAdd) {
        if (this.managedTeam.contains(developerToAdd)) return false;

        return this.managedTeam.add(developerToAdd);
    }

    /**
     * Remove um único desenvolvedor da equipe gerenciada.
     * Esta operação ocorre apenas na memória
     *
     * @param developerToRemove O objeto Developer a ser removido.
     */
    public boolean removeDeveloperFromTeam(Developer developerToRemove) {
        if (this.managedTeam.contains(developerToRemove)) return this.managedTeam.remove(developerToRemove);

        return false;
    }

    /**
     * Adiciona uma única demanda a lista de demandas gerenciados
     * Esta operação ocorre apenas na memória
     *
     * @param demandToAdd O objeto Demand a ser adicionado.
     */
    public boolean addDemandToList(Demand demandToAdd) {
        if (this.managedDemands.contains(demandToAdd)) return false;

        return this.managedDemands.add(demandToAdd);
    }

    /**
     * Adiciona um único projeto a lista de projetos gerenciados
     * Esta operação ocorre apenas na memória
     *
     * @param demandToRemove O objeto Demand a ser removida.
     */
    public boolean removeDemandFromList(Demand demandToRemove) {
        if (this.managedDemands.contains(demandToRemove)) return this.managedDemands.remove(demandToRemove);

        return false;
    }
}
