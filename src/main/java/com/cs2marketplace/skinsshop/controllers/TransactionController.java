package com.cs2marketplace.skinsshop.controllers;

import com.cs2marketplace.skinsshop.model.Skin;
import com.cs2marketplace.skinsshop.model.Transaction;
import com.cs2marketplace.skinsshop.model.User;
import com.cs2marketplace.skinsshop.services.SkinService;
import com.cs2marketplace.skinsshop.services.TransactionService;
import com.cs2marketplace.skinsshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final SkinService skinService;
    private final UserService userService;

    // Simulando uma transação de venda
    @PostMapping("/transaction")
    public Transaction createTransaction(@RequestParam Long buyerId, @RequestParam Long sellerId, @RequestParam Long skinId) {
        // Recuperando usuário comprador e vendedor e a skin
        User buyer = userService.findById(buyerId);
        User seller = userService.findById(sellerId);
        Skin skin = skinService.getSkinById(skinId);

        // Valida se a skin existe
        if (skin == null || buyer == null || seller == null) {
            throw new RuntimeException("Comprador, vendedor ou skin não encontrados");
        }

        // Alterando o status da skin para "VENDIDO"
        skin.setStatus("VENDIDO");
        skinService.updateSkin(skin);  // Atualizando a skin no banco de dados

        // Registrando a transação
        Transaction transaction = new Transaction();
        transaction.setBuyer(buyer);
        transaction.setSeller(seller);
        transaction.setSkin(skin);
        transaction.setPrice(skin.getPrice());
        transaction.setDate(LocalDateTime.now());

        // Salvando a transação
        return transactionService.saveTransaction(transaction);
    }
}
