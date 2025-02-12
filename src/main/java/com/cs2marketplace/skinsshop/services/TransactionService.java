package com.cs2marketplace.skinsshop.services;

import com.cs2marketplace.skinsshop.model.Transaction;
import com.cs2marketplace.skinsshop.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction); //
    }
}