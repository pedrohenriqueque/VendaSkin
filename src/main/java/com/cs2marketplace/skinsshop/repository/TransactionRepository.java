package com.cs2marketplace.skinsshop.repository;

import com.cs2marketplace.skinsshop.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySellerIdOrBuyerId(Long id, Long id1);
}