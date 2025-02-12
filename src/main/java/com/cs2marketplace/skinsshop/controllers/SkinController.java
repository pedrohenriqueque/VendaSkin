package com.cs2marketplace.skinsshop.controllers;

import com.cs2marketplace.skinsshop.model.Skin;
import com.cs2marketplace.skinsshop.services.SkinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skins")

public class SkinController {
    private final SkinService skinService;

    public SkinController(SkinService skinService) {
        this.skinService = skinService;
    }

    @GetMapping
    public List<Skin> getAllSkins() {
        return skinService.getAllSkins();
    }
    @GetMapping("/{id}")
    public Skin getSkinById(@PathVariable long id) {
        return  skinService.getSkinById(id);
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