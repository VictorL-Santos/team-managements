package br.com.anhembi.controller.user;

import br.com.anhembi.model.Users;
import br.com.anhembi.model.enums.UserProfileEnum;
import br.com.anhembi.service.UserService;
import br.com.anhembi.service.exception.AuthenticationException;
import br.com.anhembi.service.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public class UserController {
    private final UserService userService = new UserService();

    public void saveUser(String login, String password, String cpf, String email, UserProfileEnum role) throws Exception {
        userService.registerNewUser(login, password, cpf, email, role);
    }

    public List<Users> listAllUsers() {
        return userService.getAllUsers();
    }

    public Users login(String login, String password) throws UserNotFoundException, AuthenticationException {
        return userService.authenticateUsers(login, password);
    }

    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }

    public List<Users> getUsersByRole(UserProfileEnum userProfileEnum) {
        return userService.getUsersByRole(userProfileEnum);
    }
}
