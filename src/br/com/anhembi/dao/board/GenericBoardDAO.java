package br.com.anhembi.dao.board;

import java.util.List;
import java.util.Optional;

public interface GenericBoardDAO<T, ID> {

    /**
     * Salva uma nova entidade (INSERT).
     * @param entity O objeto da entidade a ser salvo.
     */
    void save(T entity);

    /**
     * Atualiza uma entidade existente.
     * @param entity O objeto da entidade com os dados atualizados.
     */
    void update(T entity);

    /**
     * Busca uma entidade pelo seu ID.
     * @param id O ID da entidade.
     * @return Um Optional contendo a entidade se encontrada.
     */
    Optional<T> findById(ID id);

    /**
     * Busca todas as entidades de um tipo.
     * @return Uma lista de todas as entidades.
     */
    List<T> findAll();

    /**
     * Deleta uma entidade pelo seu ID.
     * @param id O objeto da entidade a ser deletada.
     */
    void deleteById(ID id);
}
