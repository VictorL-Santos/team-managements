package br.com.anhembi.dao.user;

import br.com.anhembi.model.Users;
import br.com.anhembi.model.enums.UserProfileEnum;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    /**
     *  Salva um novo usuário ou atualiza um existente no banco de dados
     * @param user O usuário a ser salvo ou atualizado
     */
    void save(Users user);

    /**
     * Busca um usuário pelo seu identificador único
     * @param id O Long do usuário a ser encontrado
     * @return Um Optional contendo o user se encontrado, ou um Optional vazio se não
     */
    Optional<Users> findById(Long id);

    /**
     * Busca um usuário pelo seu nome de login
     * @param login O Login do usuário a ser encontrado
     * @return Um Optional contendo o user se encontrado, ou um Optional vazio se não
     */
    Optional<Users> findByLogin(String login);

    /**
     * Busca um usuário pelo hash do seu CPF.
     * @param cpfHash O hash do CPF a ser buscado.
     * @return Um Optional contendo o usuário se encontrado.
     */
    Optional<Users> findByCpfHash(String cpfHash);

    /**
     * Busca todos os usuários que pertencem a um determinado role
     * @param role O valor para filtrar os usuários
     * @return Uma lista de objetos Users. A lista estará vazia se nenhum for encontrado, mas nunca será nula
     */
    List<Users> findAllByRole(UserProfileEnum role);

    /**
     * Busca todos os usuários cadastrados no banco
     * @return Uma lista de objetos Users. A lista estará vazia se nenhum for encontrado, mas nunca será nula
     */
    List<Users> getAllUsers();

    /**
     * Deleta um usuário do banco de dados com base em seu ID
     * @param id O Long do user a ser deletado
     */
    void deleteById(Long id);

    // MÉTODOS PARA USERS ESPECÍFICOS \\

    /**
     * Salva um novo desenvolvedor na equipe do techLead no banco de dados
     * @param userId O desenvolvedor a ser salvo no time
     * @param managerId O techLead responsável pelo desenvolvedor
     */
    void updateManagerId(Long userId, Long managerId);

    /**
     * Remove um desenvolvedor da equipe do techLead no banco de dados
     * @param userId O desenvolvedor a ser removido do time
     * @param managerId O antigo techLead responsável pelo desenvolvedor
     */
    void clearManagerId(Long userId, Long managerId);

    /**
     * Busca um usuário pelo identificador único de seu techLead
     * @param id O Long do usuário a ser encontrado
     * @return Um Optional contendo o user se encontrado, ou um Optional vazio se não
     */
    List<Users> findAllByTechLeadId(Long id);

    /**
     * Salva um novo techLead na equipe do ProductOwner no banco de dados
     * @param id O techLead a ser salvo no time
     * @param productOwnerId O ProductOwner responsável pelo techLead
     */
    void updateProductOwnerID(Long id, Long productOwnerId);

    /**
     * Remove um desenvolvedor da equipe do techLead no banco de dados
     * @param id O techLead a ser removido do time
     * @param productOwnerId O antigo ProductOwner responsável pelo techLead
     */
    void clearProductOwnerID(Long id, Long productOwnerId);

    /**
     * Busca um usuário pelo identificador único de seu productOwner
     * @param id O Long do productOwner a ser encontrado
     * @return Um Optional contendo o user se encontrado, ou um Optional vazio se não
     */
    List<Users> findAllByProductOwnerId(Long id);
}
