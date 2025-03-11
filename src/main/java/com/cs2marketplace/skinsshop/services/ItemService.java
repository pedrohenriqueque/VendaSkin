package com.cs2marketplace.skinsshop.services;

import com.cs2marketplace.skinsshop.model.Item;
import com.cs2marketplace.skinsshop.model.Skin;
import com.cs2marketplace.skinsshop.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private SkinService skinService;

    public Skin addSkinToItem(Long itemId, BigDecimal price, Long sellerId) {
        // Busca o item no banco de dados
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado!"));

        // Cria a nova Skin baseada no Item
        Skin skin = new Skin();
        skin.setName(item.getName());
        skin.setImageUrl(item.getImageUrl());
        skin.setPrice(price);
        skin.setSellerid(sellerId);
        skin.setStatus("DISPONIVEL");
        skin.setRarity("NORMAL");
        skin.setTipo("NENHUM");

        // Salva a skin no banco de dados
        return skinService.addSkin(skin);
    }
}