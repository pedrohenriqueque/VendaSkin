package com.cs2marketplace.skinsshop.controllers;

import com.cs2marketplace.skinsshop.model.Transaction;
import com.cs2marketplace.skinsshop.security.JwtUtil;
import com.cs2marketplace.skinsshop.services.TransactionService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")

public class TransactionController {
    public TransactionController(TransactionService transactionService, JwtUtil jwtUtil) {
        this.transactionService = transactionService;
        this.jwtUtil = jwtUtil;
    }

    private final TransactionService transactionService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestParam Long skinId,
                                                         HttpServletRequest request) {
        // Extrai o token do cabeçalho
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7); // Remove "Bearer "

        // Extrai o userId do token
        Long buyerId = jwtUtil.extractUserId(jwt); // Método para extrair o userId do token

        // Processa a transação
        Transaction transaction = transactionService.processTransaction(buyerId, skinId);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public List<Transaction> getTransactionsByUserId(@PathVariable Long id) {
        return transactionService.getTransactionsByUserId(id);
    }

}