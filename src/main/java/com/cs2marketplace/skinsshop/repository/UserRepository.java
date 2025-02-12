package com.cs2marketplace.skinsshop.repository;

import com.cs2marketplace.skinsshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}