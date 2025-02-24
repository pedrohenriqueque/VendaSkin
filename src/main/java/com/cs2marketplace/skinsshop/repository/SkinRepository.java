package com.cs2marketplace.skinsshop.repository;

import com.cs2marketplace.skinsshop.model.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {
    List<Skin> findByStatus(String status);
}