package com.cs2marketplace.skinsshop.services;

import com.cs2marketplace.skinsshop.model.User;
import com.cs2marketplace.skinsshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User userData) {
        return userRepository.findById(userData.getId()).map(user -> {
            user.setName(userData.getName());
            user.setEmail(userData.getEmail());

            return userRepository.save(user);
        }).orElse(null);
    }

    public User updateUser(Long id, User userData) {
        return userRepository.findById(id).map(user -> {
            user.setName(userData.getName());
            user.setEmail(userData.getEmail());
            user.setBalance(userData.getBalance());
            return userRepository.save(user);
        }).orElse(null);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }


}
