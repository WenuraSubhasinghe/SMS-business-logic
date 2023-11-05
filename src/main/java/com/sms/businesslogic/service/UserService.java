package com.sms.businesslogic.service;

import com.sms.businesslogic.entity.User;
import com.sms.businesslogic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer userId){
        return userRepository.findById(userId);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Integer userId, User updateUser){
        if (userRepository.existsById(userId)){
            updateUser.setUserId(userId);
            return Optional.of(userRepository.save(updateUser));
        } else {
            return Optional.empty();
        }
    }

    public void deleteUser(Integer userId){
        userRepository.deleteById(userId);
    }
}
