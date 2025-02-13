package com.cs2marketplace.skinsshop.services;

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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // ✅ Inicializando corretamente
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // ✅ Agora a senha será criptografada
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
