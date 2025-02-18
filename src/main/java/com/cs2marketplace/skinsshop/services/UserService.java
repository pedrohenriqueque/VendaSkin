package com.cs2marketplace.skinsshop.services;

import com.cs2marketplace.skinsshop.DTO.RegisterRequest;
import com.cs2marketplace.skinsshop.model.User;
import com.cs2marketplace.skinsshop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // ✅ Adicionado corretamente


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User registerUser (RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Codifica a senha
        user.setRole("USER"); // Define a role padrão como "USER"
        return userRepository.save(user); // Salva o usuário no banco de dados
    }

    public User changeUserRole(Long userId, String newRoleName) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User  not found"));

        user.setRole(newRoleName);

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User userData) {
        return userRepository.findById(id).map(user -> {
            user.setName(userData.getName());
            user.setEmail(userData.getEmail());
            user.setBalance(userData.getBalance());

            // Se a senha for informada, criptografa antes de atualizar
            if (userData.getPassword() != null && !userData.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userData.getPassword()));
            }

            return userRepository.save(user);
        }).orElse(null);
    }

    public User updateUserBalance(Long id, BigDecimal newBalance) {
        return userRepository.findById(id).map(user -> {
            user.setBalance(newBalance);
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
