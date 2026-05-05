package com.salon.controller;

import com.salon.model.User;
import com.salon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/api/users")
    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable Long id) throws Exception {
        Optional<User> userById = userRepository.findById(id);
        if(userById.isPresent()) {
            return userById.get();
        }
        throw new Exception("User not found");
    }

    @PutMapping("/api/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) throws Exception {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new Exception("User not found wit id: "+id);
        }
        User existingUser = userById.get();
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    @DeleteMapping("/api/users/{id}")
    public String deleteUserById(@PathVariable Long id) throws Exception {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new Exception("User not exist wit id: "+id);
        }
        userRepository.deleteById(userById.get().getId());
        return "User deleted";
    }
}
