package com.cs2marketplace.skinsshop.services;



import com.cs2marketplace.skinsshop.DTO.LoginRequest;
import com.cs2marketplace.skinsshop.model.User;
import com.cs2marketplace.skinsshop.repository.UserRepository;
import com.cs2marketplace.skinsshop.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

    @Service
    public class AuthService {

        private final UserRepository userRepository;
        private final JwtUtil jwtUtil;
        private final BCryptPasswordEncoder passwordEncoder;

        public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
            this.userRepository = userRepository;
            this.jwtUtil = jwtUtil;
            this.passwordEncoder = new BCryptPasswordEncoder();
        }

        public String authenticate(LoginRequest loginRequest) {
            Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                    return jwtUtil.generateToken(user.getEmail());
                }
            }
            throw new RuntimeException("Credenciais inválidas");
        }


        public User createUser(String email, String plainPassword) {
            if (userRepository.findByEmail(email).isPresent()) {
                throw new RuntimeException("Usuário já existe");
            }

            // Criptografando a senha antes de salvar
            String encodedPassword = passwordEncoder.encode(plainPassword);

            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(encodedPassword);  // Definindo a senha criptografada

            return userRepository.save(newUser);  // Salvando o usuário no banco de dados
        }
    }


