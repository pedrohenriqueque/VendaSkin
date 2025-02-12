package com.cs2marketplace.skinsshop.services;

import com.cs2marketplace.skinsshop.model.User;
import com.cs2marketplace.skinsshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);  // Encontrando o usuário pelo ID
    }
}