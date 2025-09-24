package br.com.anhembi.service;

import br.com.anhembi.dao.board.*;
import br.com.anhembi.dao.user.UserDAO;
import br.com.anhembi.dao.user.UserDAOImpl;
import br.com.anhembi.model.Users;
import br.com.anhembi.model.developer.Developer;
import br.com.anhembi.model.enums.UserProfileEnum;
import br.com.anhembi.model.product.Product;
import br.com.anhembi.model.product.demand.Demand;
import br.com.anhembi.model.product.task.Task;
import br.com.anhembi.model.product_owner.ProductOwner;
import br.com.anhembi.model.tech_lead.TechLead;
import br.com.anhembi.security.PasswordUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {
    private final UserDAO userDAO = new UserDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final DemandDAO demandDAO = new DemandDAOImpl();
    private final TaskDAO taskDAO = new TaskDAOImpl();

    /**
     * Orquestra o processo de registro de um novo usuário
     *
     * @param login    O login do novo usuário
     * @param password A senha em texto puro (que será hasheada)
     * @param cpf      O CPF
     * @param email    O Email
     * @param role     O PAPEL do usuário a ser criado
     * @throws Exception se a validação falhar
     */
    public void registerNewUser(String login, String password, String cpf, String email, UserProfileEnum role) throws Exception {
        if (login.length() < 5) throw new Exception("Login deve ter pelo menos 5 caracteres.");
        if (password.length() < 8) throw new Exception("Senha deve ter pelo menos 8 caracteres.");

        String hashSenha = PasswordUtils.hashPassword(password);

        Users newUser = switch (role) {
            case DEVELOPER -> new Developer(login, hashSenha, cpf, email);
            case TECH_LEAD -> new TechLead(login, hashSenha, cpf, email);
            case PRODUCT_OWNER -> new ProductOwner(login, hashSenha, cpf, email);
            default -> throw new IllegalArgumentException("Tipo de usuário inválido: " + role);
        };

        userDAO.save(newUser);
    }

    public List<Users> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * Autentica um usuário com base no login e na senha.
     *
     * @param login    O login fornecido.
     * @param password A senha em texto puro fornecida.
     * @return Um Optional contendo o objeto Users se a autenticação for bem-sucedida,
     * ou um Optional vazio caso contrário.
     */
    public Optional<Users> authenticateUsers(String login, String password) {
        Optional<Users> optionalUser = userDAO.findByLogin(login);

        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();

            if (PasswordUtils.checkPassword(password, user.getPassword())) return Optional.of(user);
        }

        return Optional.empty();
    }

    /**
     * Busca um único usuário pelo seu ID.
     *
     * @param id O ID do usuário.
     * @return Um Optional contendo o usuário, se encontrado.
     */
    public Optional<Users> getUserById(Long id) {
        return userDAO.findById(id);
    }

    /**
     * Busca uma lista de usuários por seus roles.
     *
     * @param role O role dos usuários.
     * @return Um List contendo os usuários, se encontrado.
     */
    public List<Users> getUsersByRole(UserProfileEnum role) {
        return userDAO.findAllByRole(role);
    }

    /**
     * Atualiza as informações de um usuário.
     *
     * @param userId   O ID do usuário a ser atualizado.
     * @param newLogin O novo login.
     * @param newEmail O novo email.
     * @throws Exception se o usuário não for encontrado.
     */
    public void updateUserInfo(Long userId, String newLogin, String newEmail) throws Exception {
        Users user = userDAO.findById(userId)
                .orElseThrow(() -> new Exception("Usuário com ID " + userId + " não encontrado para atualização."));

        user.setLogin(newLogin);
        user.setEmail(newEmail);

        userDAO.save(user);
    }

    public void addNewDeveloperToTechLead(Long userId, Long managerId) {
        Optional<Users> user = userDAO.findById(userId);
        Optional<Users> manager = userDAO.findById(managerId);

        if (user.isEmpty()) {
            return;
        }
        if (!(user.get() instanceof Developer developer)) {
            return;
        }
        if (manager.isEmpty()) {
            return;
        }
        if (!(manager.get() instanceof TechLead techLead)) {
            return;
        }

        developer.setTechLead(techLead);
        techLead.addDeveloperToTeam(developer);

        userDAO.updateManagerId(userId, managerId);
    }

    public void removeDeveloperFromTechLead(Long userId, Long managerId) {
        Optional<Users> user = userDAO.findById(userId);
        Optional<Users> manager = userDAO.findById(managerId);

        if (user.isEmpty()) {
            return;
        }
        if (!(user.get() instanceof Developer developer)) {
            return;
        }
        if (manager.isEmpty()) {
            return;
        }
        if (!(manager.get() instanceof TechLead techLead)) {
            return;
        }

        developer.setTechLead(null);
        techLead.removeDeveloperFromTeam(developer);

        userDAO.clearManagerId(userId, managerId);
    }

    public void addNewTechLeadToPO(Long userId, Long productOwnerId) {
        Optional<Users> manager = userDAO.findById(userId);
        Optional<Users> po = userDAO.findById(productOwnerId);

        if (manager.isEmpty()) {
            return;
        }
        if (!(manager.get() instanceof TechLead techLead)) {
            return;
        }
        if (po.isEmpty()) {
            return;
        }
        if (!(po.get() instanceof ProductOwner productOwner)) {
            return;
        }

        techLead.setProductOwner(productOwner);
        productOwner.addTechLeadToTeam(techLead);

        userDAO.updateProductOwnerID(userId, productOwnerId);
    }

    public void removeTechLeadToPO(Long userId, Long productOwnerId) {
        Optional<Users> manager = userDAO.findById(userId);
        Optional<Users> po = userDAO.findById(productOwnerId);

        if (manager.isEmpty()) {
            return;
        }
        if (!(manager.get() instanceof TechLead techLead)) {
            return;
        }
        if (po.isEmpty()) {
            return;
        }
        if (!(po.get() instanceof ProductOwner productOwner)) {
            return;
        }

        techLead.setProductOwner(null);
        productOwner.removeTechLeadFromTeam(techLead);

        userDAO.clearProductOwnerID(userId, productOwnerId);
    }

    /**
     * Busca um usuário pelo seu ID e o retorna totalmente "hidratado" com suas relações.
     *
     * @param id O ID do usuário a ser buscado.
     * @return Um Optional contendo o usuário completo, se encontrado.
     */
    public Optional<Users> getFullUserDetails(Long id) {
        Optional<Users> optionalUser = userDAO.findById(id);

        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();

            switch (user) {
                case Developer developer -> {
                    List<Task> assignedTasks = taskDAO.findAllByAssignedDeveloper(developer.getId());
                    developer.setAssignedTasks(assignedTasks);
                }
                case TechLead techLead -> {
                    List<Demand> assignedDemands = demandDAO.findAllByTechLead(techLead.getId());
                    List<Users> managedUsers = userDAO.findAllByTechLeadId(techLead.getId());
                    List<Developer> managedTeam = managedUsers.stream().filter(u -> u instanceof Developer).map(u -> (Developer) u).collect(Collectors.toList());
                    techLead.setManagedDemands(assignedDemands);
                    techLead.setManagedTeam(managedTeam);
                }
                case ProductOwner productOwner -> {
                    List<Product> ownedProducts = productDAO.findAllByProductOwner(productOwner.getId());
                    List<Users> managedUsers = userDAO.findAllByProductOwnerId(productOwner.getId());
                    List<TechLead> managedTeam = managedUsers.stream().filter(u -> u instanceof TechLead).map(u -> (TechLead) u).collect(Collectors.toList());
                    productOwner.setOwnedProducts(ownedProducts);
                    productOwner.setManagedTechLeads(managedTeam);
                }
                default -> throw new IllegalStateException("Unexpected value: " + user);
            }

        }
        return optionalUser;
    }

    /**
     * Deleta um usuário pelo seu ID.
     *
     * @param id O ID do usuário a ser deletado.
     */
    public void deleteUser(Long id) {
        userDAO.deleteById(id);
    }
}
