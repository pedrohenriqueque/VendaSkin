package com.cs2marketplace.skinsshop.repository;

import com.cs2marketplace.skinsshop.model.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {
}