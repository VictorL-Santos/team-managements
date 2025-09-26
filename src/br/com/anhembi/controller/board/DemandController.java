package br.com.anhembi.controller.board;

import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.demand.Demand;
import br.com.anhembi.service.DemandService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller para a entidade Demand.
 * Delega as solicitações da View para o DemandService.
 */
public class DemandController {
    private final DemandService demandService = new DemandService();

    public void createDemand(String title, String description, Long techLeadId, UUID productId) throws Exception {
        demandService.createNewDemand(title, description,techLeadId, productId);
    }

    public Optional<Demand> getDemandDetails(UUID id) {
        return demandService.getFullDemandDetails(id);
    }

    public void assignTechLead(UUID demandId, Long techLeadId) throws Exception {
        demandService.assignTechLeadToDemand(demandId, techLeadId);
    }

    public void changeStatus(UUID demandId, ScrumStatusEnum newStatus) throws Exception {
        demandService.changeDemandStatus(demandId, newStatus);
    }

    public void deleteDemand(UUID id) {
        demandService.deleteDemand(id);
    }

    public List<Demand> listAllDemands() {
        return demandService.getAllDemands();
    }
}
