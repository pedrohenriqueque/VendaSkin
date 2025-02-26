package com.cs2marketplace.skinsshop.controllers;

import com.cs2marketplace.skinsshop.model.Item;
import com.cs2marketplace.skinsshop.model.Skin;
import com.cs2marketplace.skinsshop.repository.ItemRepository;
import com.cs2marketplace.skinsshop.repository.SkinRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }


    @GetMapping("/fetch")
    public ResponseEntity<Map<String, String>> fetchItems() {
        String url = "https://api.steamapis.com/image/items/730"; // URL da API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, String> items = response.getBody();

            // Salvar os skins no banco de dados
            for (Map.Entry<String, String> entry : items.entrySet()) {
                Item item = new Item();
                item.setName(entry.getKey());
                item.setImageUrl(entry.getValue());
                itemRepository.save(item);

            }

            return ResponseEntity.ok(items);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
