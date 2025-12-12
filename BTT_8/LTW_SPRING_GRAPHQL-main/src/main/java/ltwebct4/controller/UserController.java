package ltwebct4.controller;

import ltwebct4.entity.User;
import ltwebct4.input.UserInput;
import ltwebct4.repository.UserRepository;
import ltwebct4.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // --- QUERY (READ) ---
    @QueryMapping
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @QueryMapping
    public Optional<User> findUserById(@Argument Long id) {
        return userRepository.findById(id);
    }
    
    // --- MUTATION (CRUD) ---

    @MutationMapping
    public User createUser(@Argument UserInput user) {
        return userService.createUser(user);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument UserInput user) {
        return userService.updateUser(id, user);
    }

    @MutationMapping
    public User deleteUser(@Argument Long id) {
        return userService.deleteUser(id);
    }
}