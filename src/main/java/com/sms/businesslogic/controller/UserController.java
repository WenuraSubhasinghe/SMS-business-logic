package com.sms.businesslogic.controller;

import com.sms.businesslogic.entity.User;
import com.sms.businesslogic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User updatedUser) {
        Optional<User> updatedUserOpt = userService.updateUser(userId, updatedUser);
        return updatedUserOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
