package com.cs2marketplace.skinsshop.services;

import com.cs2marketplace.skinsshop.model.Transaction;
import com.cs2marketplace.skinsshop.model.User;
import com.cs2marketplace.skinsshop.model.Skin;
import com.cs2marketplace.skinsshop.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final SkinService skinService;

    public TransactionService(TransactionRepository transactionRepository, UserService userService, SkinService skinService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.skinService = skinService;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction processTransaction(Long buyerId, Long skinId) {
        User buyer = userService.findById(buyerId);
        Skin skin = skinService.getSkinById(skinId);
        Long sellerId =   skin.getSellerid() ;
        if(buyer.getId().equals(sellerId)) {
            throw new RuntimeException("Você já é o vendedor");
        }
        User seller = userService.findById(sellerId);

        if (skin == null || buyer == null || seller == null) {
            throw new RuntimeException("Comprador, vendedor ou skin não encontrados");
        }

        BigDecimal price = skin.getPrice();
        if (buyer.getBalance().compareTo(price) < 0) {
            throw new RuntimeException("Comprador não possui saldo suficiente");
        }


        buyer.setBalance(buyer.getBalance().subtract(price));
        seller.setBalance(seller.getBalance().add(price));





        userService.updateUserBalance(buyerId,buyer.getBalance());
        userService.updateUserBalance(sellerId,seller.getBalance());
        skinService.updateSkinStatus(skinId,"VENDIDO");


        Transaction transaction = new Transaction();
        transaction.setBuyer(buyer);
        transaction.setSeller(seller);
        transaction.setSkin(skin);
        transaction.setPrice(price);
        transaction.setDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByUserId(Long id) {
        return transactionRepository.findBySellerIdOrBuyerId(id, id);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
