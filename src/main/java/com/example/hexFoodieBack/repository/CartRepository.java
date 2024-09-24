package com.example.hexFoodieBack.repository;

import com.example.hexFoodieBack.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("Select u from Cart u Where u.user.email=:email")
    Cart findUserByEmail(@Param("email") String email);
}
