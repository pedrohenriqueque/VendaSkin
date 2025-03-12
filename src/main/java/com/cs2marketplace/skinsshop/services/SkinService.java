package com.cs2marketplace.skinsshop.services;

import com.cs2marketplace.skinsshop.repository.SkinRepository;
import com.cs2marketplace.skinsshop.model.Skin;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SkinService {

    private final SkinRepository skinRepository;


    public SkinService(SkinRepository skinRepository) {
        this.skinRepository = skinRepository;
    }

    public List<Skin> getAllSkins() {
        return skinRepository.findAll();
    }

    public Skin getSkinById(Long id) {
        if(skinRepository.existsById(id)){
            return skinRepository.findById(id).get();
        }
        else{
            throw new RuntimeException("Skin not found with id: " + id);
        }
    }

    public Skin addSkin(Skin skin) {
        skin.setStatus("DISPONÍVEL");
        return skinRepository.save(skin);
    }

    public void deleteSkin(Long id) {
        if (skinRepository.existsById(id)) {
            skinRepository.deleteById(id);
        } else {
            throw new RuntimeException("Skin not found with id: " + id);
        }
    }

    public List<Skin> addSkins(List<Skin> skins) {
        skins.forEach(skin -> skin.setStatus("DISPONÍVEL"));
        return skinRepository.saveAll(skins);
    }
    public Skin updateSkinStatus(Long id, String newStatus) {
        return skinRepository.findById(id)
                .map(skin -> {
                    skin.setStatus(newStatus);
                    return skinRepository.save(skin);
                })
                .orElseThrow(() -> new RuntimeException("Skin não encontrada com o ID: " + id));
    }

    public Skin updateSkinPrice(Long id, BigDecimal newPrice) {
        return skinRepository.findById(id)
                .map(skin -> {
                    skin.setPrice(newPrice);
                    return skinRepository.save(skin);
                })
                .orElseThrow(() -> new RuntimeException("Skin não encontrada com o ID: " + id));
    }

    public Skin updateSkin(Skin skin) {
        return skinRepository.save(skin);
    }

    public List<Skin> findBySellerid(Long sellerid) {
        return skinRepository.findBySellerid(sellerid);
    }

}
