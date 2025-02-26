package com.cs2marketplace.skinsshop.repository;


import com.cs2marketplace.skinsshop.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
