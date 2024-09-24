package com.example.hexFoodieBack.service;

import com.example.hexFoodieBack.entity.*;
import com.example.hexFoodieBack.repository.*;
import com.example.hexFoodieBack.request.CartItemRequest;
import com.example.hexFoodieBack.request.CartRequest;
import com.example.hexFoodieBack.request.EmailRequest;
import com.example.hexFoodieBack.response.CartResponse;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartServiceImple implements CartService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    CartItemRepository cartItemRepository;

//    private CartResponse cartResponse;

    @Override
    public ResponseEntity<CartResponse> createCart(CartRequest cartRequest) {
        System.out.println(cartRequest.getEmail());
        User user = userRepository.findByEmail(cartRequest.getEmail());
        if(user==null){
            log.info("user not found");
            CartResponse cartResponse=new CartResponse();
            cartResponse.setMessage("User not found");
            cartResponse.setSuccess(false);
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
        Cart cart=cartRepository.findUserByEmail(cartRequest.getEmail());
        if(cart!=null) {
            log.info("Already exist");
            CartResponse cartResponse=new CartResponse();
            cartResponse.setSuccess(false);
            cartResponse.setMessage("Already exist");
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);
        }
        Restaurant restaurant=restaurantRepository.findByRestaurantId(cartRequest.getId());
        System.out.println(user);
        Cart cart1 = new Cart();
        cart1.setUser(user);
        cart1.setRestaurant(restaurant);
        cartRepository.save(cart1);
        CartResponse cartResponse = new CartResponse();
        cartResponse.setMessage("Cart Created Successfully");
        log.info("Cart Created Successfully");
        cartResponse.setSuccess(true);
        return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<CartResponse> addCartItem(CartItemRequest cartItemRequest) {
        Cart cart=cartRepository.findUserByEmail(cartItemRequest.getEmail());
        if(cart==null){
            CartResponse cartResponse=new CartResponse();
            cartResponse.setSuccess(false);
            cartResponse.setMessage("Cart not Created");
            log.info("Cart not Created");
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);
        }
        System.out.println(cart);
        System.out.println(cartItemRequest.getId());
        Food food=foodRepository.findFoodById(cartItemRequest.getId());
      CartItems exist=cartItemRepository.cartItemExist(cartItemRequest.getEmail(),food,cart);
        if(exist==null) {
            // System.out.println(addItemRequest.getQuantity() +"   "+product.getPrice());
            CartItems cartItems = new CartItems();
            cartItems.setFood(food);
            cartItems.setCart(cart);
            cartItems.setQuantity(1);
            cartItems.setEmail(cartItemRequest.getEmail());
            cartItems.setPrice(food.getPrice()*cartItems.getQuantity());
            cartItemRepository.save(cartItems);
            cart.getCartItems().add(cartItems);
            Cart cart1=findUserCart(cartItemRequest.getEmail());
            cartRepository.save(cart1);
            CartResponse cartResponse=new CartResponse();
            cartResponse.setMessage("Food Items added to cart successfully");
            cartResponse.setSuccess(true);
            log.info("Food Items added to cart successfully");
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);
        }
        else {
            exist.setQuantity(exist.getQuantity()+1);
            exist.setPrice(food.getPrice()* exist.getQuantity());
            cartItemRepository.save(exist);
            cart.getCartItems().add(exist);
            Cart cart1=findUserCart(cartItemRequest.getEmail());
            cartRepository.save(cart1);
            CartResponse cartResponse=new CartResponse();
            cartResponse.setMessage("Repeated Last Item");
            cartResponse.setSuccess(true);
            log.info("Repeated Last Item");
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);
        }

    }

    @Override
    public Cart findUserCart(String email){
            Cart cart=cartRepository.findUserByEmail(email);
            int totalPrice=0;
            int totalItem=0;
            for(CartItems cartItems:cart.getCartItems()){
                totalPrice+=cartItems.getPrice();
                totalItem+=cartItems.getQuantity();
            }
            cart.setTotalPrice(totalPrice);
            cart.setTotalItems(totalItem);
            return cartRepository.save(cart);
    }

    @Override
    public ResponseEntity<CartResponse> removeCartItem(CartItemRequest cartItemRequest) {
        System.out.println(cartItemRequest.getId());
        CartItems cartItems=cartItemRepository.findByItemId(cartItemRequest.getId(), cartItemRequest.getEmail());
        Food food=foodRepository.findFoodById(cartItemRequest.getId());
        if(cartItems==null){
            log.info("Item Not Found");
            CartResponse cartResponse=new CartResponse();
            cartResponse.setMessage("Item not found");
            cartResponse.setSuccess(false);
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);
        }
        if(cartItems.getQuantity()>1) {
            cartItems.setQuantity(cartItems.getQuantity()-1);
            cartItems.setPrice(food.getPrice()* cartItems.getQuantity());
            cartItemRepository.save(cartItems);
            Cart cart = findUserCart(cartItemRequest.getEmail());
            cartRepository.save(cart);
            log.info("Quantity Decreased");
            CartResponse cartResponse=new CartResponse();
            cartResponse.setMessage("Item removed");
            cartResponse.setSuccess(false);
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);

        }
        Cart cart=cartRepository.findUserByEmail(cartItemRequest.getEmail());
        if(cart.getTotalItems()<2) {
            cartItemRepository.delete(cartItems);
            Cart cart1 = findUserCart(cartItemRequest.getEmail());
            cartRepository.delete(cart1);
            log.info("Item removed");
            CartResponse cartResponse = new CartResponse();
            cartResponse.setMessage("Item removed");
            cartResponse.setSuccess(false);
            return new ResponseEntity<>(cartResponse, HttpStatus.OK);
        }
        cartItemRepository.delete(cartItems);
        Cart cart1 = findUserCart(cartItemRequest.getEmail());
        cartRepository.save(cart1);
        log.info("Item removed");
        CartResponse cartResponse = new CartResponse();
        cartResponse.setMessage("Item removed");
        cartResponse.setSuccess(false);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Cart> displayCart(EmailRequest emailRequest) {
        Cart cart=cartRepository.findUserByEmail(emailRequest.getEmail());
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

    public ResponseEntity<List<CartItems>> displayItems(EmailRequest emailRequest){
        List<CartItems> cartItems=cartItemRepository.findByEmail(emailRequest.getEmail());
        return new ResponseEntity<>(cartItems,HttpStatus.OK);
    }
}
