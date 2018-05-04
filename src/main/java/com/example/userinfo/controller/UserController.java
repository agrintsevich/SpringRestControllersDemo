package com.example.userinfo.controller;

import com.example.userinfo.exception.ResourceNotFoundException;
import com.example.userinfo.model.User;
import com.example.userinfo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody final User user) {
        return (User) this.userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") final Long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") final Long userId,
                           @Valid @RequestBody final User userDetails) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setName(userDetails.getName());
        user.setContent(userDetails.getContent());

        final User updatedUser = (User) this.userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") final Long userId) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}
