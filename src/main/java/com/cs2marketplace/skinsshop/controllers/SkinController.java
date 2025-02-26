package com.cs2marketplace.skinsshop.controllers;

import com.cs2marketplace.skinsshop.model.Item;
import com.cs2marketplace.skinsshop.model.Skin;
import com.cs2marketplace.skinsshop.repository.ItemRepository;
import com.cs2marketplace.skinsshop.repository.SkinRepository;
import com.cs2marketplace.skinsshop.security.JwtUtil;
import com.cs2marketplace.skinsshop.services.SkinService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skins")

public class SkinController {
    private final SkinService skinService;
    private final SkinRepository skinRepository;
    private final JwtUtil jwtUtil;
    private final ItemRepository itemRepository;

    public SkinController(SkinService skinService, SkinRepository skinRepository, JwtUtil jwtUtil, ItemRepository itemRepository) {
        this.skinService = skinService;
        this.skinRepository= skinRepository;
        this.jwtUtil = jwtUtil;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<Skin> getAllSkins() {
        return skinService.getAllSkins();
    }
    @GetMapping("/{id}")
    public Skin getSkinById(@PathVariable long id) {
        return  skinService.getSkinById(id);
    }
    @GetMapping("/disponivel")
    public List<Skin> getAllAvailableSkins() {
        return skinRepository.findByStatus("DISPONIVEL"); // Retorna skins com status "DISPONIVEL"
    }
    @GetMapping("/fetch")
    public ResponseEntity<Map<String, String>> fetchSkins() {
        String url = "https://api.steamapis.com/image/items/730"; // URL da API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, String> skins = response.getBody();

            // Salvar os skins no banco de dados
            for (Map.Entry<String, String> entry : skins.entrySet()) {
                Skin skin = new Skin();
                skin.setName(entry.getKey());
                skin.setImageUrl(entry.getValue());
                skin.setPrice(BigDecimal.valueOf(Math.random()));
                skinRepository.save(skin);

            }

            return ResponseEntity.ok(skins);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping
    public Skin addSkin(@RequestBody Skin skin) {
        return skinService.addSkin(skin);
    }

    @DeleteMapping("/{id}")
    public void deleteSkin(@PathVariable Long id){
        skinService.deleteSkin(id);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Skin>> addSkins(@RequestBody List<Skin> skins) {
        return ResponseEntity.ok(skinService.addSkins(skins));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Skin> updateSkinStatus(@PathVariable Long id, @RequestBody Map<String, String> update) {
        String newStatus = update.get("status");
        Skin updated = skinService.updateSkinStatus(id, newStatus);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<Skin> updateSkinPrice(@PathVariable Long id, @RequestBody Map<String, BigDecimal> update) {
        BigDecimal newPrice = update.get("price");
        Skin updated = skinService.updateSkinPrice(id, newPrice);
        return ResponseEntity.ok(updated);
    }


    @PostMapping("/add")
    public ResponseEntity<Skin> addSkinNew(
            @RequestParam Long id,
            @RequestParam BigDecimal price,
            HttpServletRequest request) {

        // Verifica se o Authorization header está presente
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Extrai o token JWT e o userId
        String jwt = authorizationHeader.substring(7);
        Long sellerId = jwtUtil.extractUserId(jwt);
        System.out.println("Usuário autenticado: " + sellerId);
        if (sellerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Busca o item no banco de dados
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Cria a nova Skin associada ao Item
        Skin skin = new Skin();
        skin.setName(item.getName());
        skin.setImageUrl(item.getImageUrl());
        skin.setPrice(price);
        skin.setSellerid(sellerId);
        skin.setStatus("DISPONIVEL");
        skin.setRarity("NORMAL");
        skin.setTipo("NENHUM");

        Skin savedSkin = skinService.addSkin(skin);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSkin);
    }

}