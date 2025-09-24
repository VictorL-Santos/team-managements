package br.com.anhembi.dao.board;

import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.demand.Demand;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DemandDAO extends GenericBoardDAO<Demand, UUID> {
    List<Demand> findAllByProductId(UUID productId);

    List<Demand> findAllByTechLead(Long ownerId);

    List<Demand> findAllByScrumStatus(ScrumStatusEnum status);

    List<Demand> findByTitle(String title);
}
