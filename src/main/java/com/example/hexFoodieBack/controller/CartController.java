package com.example.hexFoodieBack.controller;

import com.example.hexFoodieBack.entity.Cart;
import com.example.hexFoodieBack.entity.CartItems;
import com.example.hexFoodieBack.request.CartItemRequest;
import com.example.hexFoodieBack.request.CartRequest;
import com.example.hexFoodieBack.request.EmailRequest;
import com.example.hexFoodieBack.response.CartResponse;
import com.example.hexFoodieBack.service.CartServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class CartController {
    @Autowired
    CartServiceImple cartServiceImple;
    @PostMapping("/createCart")
    public ResponseEntity<CartResponse> createCart(@RequestBody CartRequest cartRequest){
        return cartServiceImple.createCart(cartRequest);
    }
    @PostMapping("/addCartItem")
    public ResponseEntity<CartResponse> addCartItem(@RequestBody CartItemRequest cartItemRequest){
        return cartServiceImple.addCartItem(cartItemRequest);
    }
    @DeleteMapping("/removecartitem")
    public ResponseEntity<CartResponse> removeCartItem(@RequestBody CartItemRequest cartItemRequest){
        return cartServiceImple.removeCartItem(cartItemRequest);
    }

    @PostMapping("/displaycart")
    public ResponseEntity<Cart> displaycart(@RequestBody EmailRequest emailRequest){
        return cartServiceImple.displayCart(emailRequest);
    }
    @PostMapping("/displaycartitems")
    public ResponseEntity<List<CartItems>> displayItems(@RequestBody EmailRequest emailRequest){
        return cartServiceImple.displayItems(emailRequest);
    }
}
