package com.cs2marketplace.skinsshop.controllers;

import com.cs2marketplace.skinsshop.model.Transaction;
import com.cs2marketplace.skinsshop.services.TransactionService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")

public class TransactionController {
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private final TransactionService transactionService;

    @PostMapping
    public Transaction createTransaction(@RequestParam Long buyerId,
                                         @RequestParam Long sellerId,
                                         @RequestParam Long skinId) {
        return transactionService.processTransaction(buyerId, sellerId, skinId);
    }
}