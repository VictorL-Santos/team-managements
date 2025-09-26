package br.com.anhembi.service;

import br.com.anhembi.dao.board.*;
import br.com.anhembi.dao.user.UserDAO;
import br.com.anhembi.dao.user.UserDAOImpl;
import br.com.anhembi.model.Users;
import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.Product;
import br.com.anhembi.model.product.demand.Demand;
import br.com.anhembi.model.tech_lead.TechLead;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Camada de Serviço para a entidade Demand.
 */
public class DemandService {
    private final DemandDAO demandDAO = new DemandDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final TaskDAO taskDAO = new TaskDAOImpl();

    /**
     * Cria uma nova demanda para um produto existente.
     */
    public void createNewDemand(String title, String description, Long techLeadId, UUID productId) throws Exception {
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new Exception("Produto com ID " + productId + " não encontrado."));

        Demand newDemand = new Demand(title, description, product, techLeadId, productId);

        demandDAO.save(newDemand);
    }

    /**
     * Busca uma demanda pelo ID e a retorna totalmente hidratada.
     */
    public Optional<Demand> getFullDemandDetails(UUID id) {
        Optional<Demand> optionalDemand = demandDAO.findById(id);

        optionalDemand.ifPresent(demand -> {
            demand.setTaskList(taskDAO.findAllByDemandId(demand.getId()));
        });

        return optionalDemand;
    }

    /**
     * Atribui uma demanda a um Tech Lead.
     */
    public void assignTechLeadToDemand(UUID demandId, Long techLeadId) throws Exception {
        Demand demand = demandDAO.findById(demandId)
                .orElseThrow(() -> new Exception("Demanda com ID " + demandId + " não encontrada."));

        TechLead techLead = (TechLead) userDAO.findById(techLeadId)
                .filter(user -> user instanceof TechLead)
                .orElseThrow(() -> new Exception("Tech Lead com ID " + techLeadId + " não encontrado."));

        demand.setAssignedTechLead(techLead);

        demandDAO.update(demand);
    }

    /**
     * Altera o status de uma demanda.
     */
    public void changeDemandStatus(UUID demandId, ScrumStatusEnum newStatus) throws Exception {
        Demand demand = demandDAO.findById(demandId)
                .orElseThrow(() -> new Exception("Demanda com ID " + demandId + " não encontrada."));

        demand.setStatus(newStatus);

        demandDAO.update(demand);
    }

    /**
     * Deleta uma demanda pelo seu ID.
     */
    public void deleteDemand(UUID id) {
        // Adicionar lógica de negócio aqui, se necessário
        demandDAO.deleteById(id);
    }

    public List<Demand> getAllDemands() {
        return demandDAO.findAll();
    }
}
