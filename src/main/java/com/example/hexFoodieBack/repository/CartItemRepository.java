package com.example.hexFoodieBack.repository;

import com.example.hexFoodieBack.entity.Cart;
import com.example.hexFoodieBack.entity.CartItems;
import com.example.hexFoodieBack.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItems,Long> {
    @Query("Select c from CartItems c Where c.food.id=:id And c.email=:email")
    CartItems findByItemId(@Param("id") Long id,@Param("email") String email);
    @Query("SELECT i from CartItems i Where i.cart=:cart And i.food=:food And i.email=:email")
    CartItems cartItemExist(@Param("email") String email, @Param("food") Food food, @Param("cart") Cart cart);

    List<CartItems> findByEmail(String email);
}
