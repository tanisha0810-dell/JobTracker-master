package org.example.service;

import dao.UserRepository;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.orElse(null);
    }
}
