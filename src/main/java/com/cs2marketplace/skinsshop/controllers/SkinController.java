package com.cs2marketplace.skinsshop.controllers;

import com.cs2marketplace.skinsshop.model.Skin;
import com.cs2marketplace.skinsshop.repository.SkinRepository;
import com.cs2marketplace.skinsshop.services.SkinService;
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

    public SkinController(SkinService skinService, SkinRepository skinRepository) {
        this.skinService = skinService;
        this.skinRepository= skinRepository;
    }

    @GetMapping
    public List<Skin> getAllSkins() {
        return skinService.getAllSkins();
    }
    @GetMapping("/{id}")
    public Skin getSkinById(@PathVariable long id) {
        return  skinService.getSkinById(id);
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
                skin.setRarity("vermelha");
                skin.setTipo("teste");
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
}