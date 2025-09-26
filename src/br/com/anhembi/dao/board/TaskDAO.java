package br.com.anhembi.dao.board;

import br.com.anhembi.model.enums.ScrumStatusEnum;
import br.com.anhembi.model.product.task.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskDAO extends GenericBoardDAO<Task, UUID>{
    List<Task> findAllByDemandId(UUID demandId);

    List<Task> findAllByAssignedDeveloper(Long ownerId);

    List<Task> findAllByScrumStatus(ScrumStatusEnum status);

    List<Task> findByTitle(String title);
}
