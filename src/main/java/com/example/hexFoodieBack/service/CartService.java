package com.example.hexFoodieBack.service;

import com.example.hexFoodieBack.entity.Cart;
import com.example.hexFoodieBack.entity.CartItems;
import com.example.hexFoodieBack.entity.User;
import com.example.hexFoodieBack.request.CartItemRequest;
import com.example.hexFoodieBack.request.CartRequest;
import com.example.hexFoodieBack.request.EmailRequest;
import com.example.hexFoodieBack.response.CartResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    ResponseEntity<CartResponse> createCart(CartRequest cartRequest);
    ResponseEntity<CartResponse> addCartItem(CartItemRequest cartItemRequest);

    Cart findUserCart(String email);

    ResponseEntity<CartResponse> removeCartItem(CartItemRequest cartItemRequest);

    ResponseEntity<Cart> displayCart(EmailRequest emailRequest);

    ResponseEntity<List<CartItems>> displayItems(EmailRequest emailRequest);
}
