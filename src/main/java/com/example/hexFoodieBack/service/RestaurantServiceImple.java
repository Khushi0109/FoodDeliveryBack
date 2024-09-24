package com.example.hexFoodieBack.service;


import com.example.hexFoodieBack.entity.Food;
import com.example.hexFoodieBack.entity.Order;
import com.example.hexFoodieBack.entity.Restaurant;
import com.example.hexFoodieBack.entity.User;
import com.example.hexFoodieBack.repository.FoodRepository;
import com.example.hexFoodieBack.repository.OrderRepository;
import com.example.hexFoodieBack.repository.RestaurantRepository;
import com.example.hexFoodieBack.repository.UserRepository;
import com.example.hexFoodieBack.request.*;
import com.example.hexFoodieBack.response.MenuResponse;
import com.example.hexFoodieBack.response.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class RestaurantServiceImple implements RestaurantService{

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

//    private MenuResponse menuResponse;

    @Override
    public ResponseEntity<MenuResponse> addMenuItems(MenuRequest menuRequest) {
        Food food=foodRepository.findFoodByName(menuRequest.getName());
        Restaurant restaurant=restaurantRepository.findByName(menuRequest.getRestaurantName());
        if(food==null){
            Food food1=new Food();
            food1.setName(menuRequest.getName());
            food1.setCategory(menuRequest.getCategory());
            food1.setImageUrl(menuRequest.getImageUrl());
            food1.setPrice(menuRequest.getPrice());
            food1.setRestaurantId(restaurant);
            foodRepository.save(food1);
            MenuResponse menuResponse=new MenuResponse();
            menuResponse.setMessage("Food Item added successfully");
            menuResponse.setSuccess(true);
            return new ResponseEntity<>(menuResponse, HttpStatus.OK);
        }
        else {
            MenuResponse menuResponse=new MenuResponse();
            menuResponse.setMessage("Food Item already present");
            menuResponse.setSuccess(false);
            return new ResponseEntity<>(menuResponse,HttpStatus.OK);
        }
    }
    public ResponseEntity<MenuResponse> deleteMenuItems(DeleteFoodRequest deleteFoodRequest){
        Food food=foodRepository.findFoodById(deleteFoodRequest.getId());
        if(food==null){
            MenuResponse menuResponse=new MenuResponse();
            menuResponse.setMessage("No food item found");
            menuResponse.setSuccess(false);
            return new ResponseEntity<>(menuResponse,HttpStatus.OK);
        }
        foodRepository.delete(food);
        MenuResponse menuResponse=new MenuResponse();
        menuResponse.setSuccess(true);
        menuResponse.setMessage("Food Item deleted Successfully");
        return new ResponseEntity<>(menuResponse,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Food>> displayMenu(RestaurantIdRequest restaurantIdRequest) {
        List<Food> foods=foodRepository.findByRestaurant(restaurantIdRequest.getRestaurantId());
        log.info(foods.toString());
        if(foods==null){
            return null;
        }
        return new ResponseEntity<>(foods,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Food>> getMyMenu(EmailRequest emailRequest) {
        User user=userRepository.findByEmail(emailRequest.getEmail());
        List<Food> foods=foodRepository.findByRestaurantName(user.getName());
        return new ResponseEntity<>(foods,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StatusResponse> orderState(StatusRequest statusRequest) {
        Order order=orderRepository.findByOrderId(statusRequest.getId());
        User user=userRepository.findByEmail(statusRequest.getEmail());
        order.setOrderStatus("Confirmed");
        orderRepository.save(order);
        StatusResponse statusResponse=new StatusResponse();
        statusResponse.setMessage("Order Accepted");
        statusResponse.setSuccess(true);
        String emailBody = "This is to notify you that your order is Accepted by restaurant and soon will be delivered to you";
       // sendEmail(user.getEmail(),"Order Accepted",emailBody);
        return new ResponseEntity<>(statusResponse,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Order>> getUserOrders(EmailRequest emailRequest) {
        log.info(emailRequest.getEmail());
        User user=userRepository.findByEmail(emailRequest.getEmail());
        List<Order> orders=orderRepository.getUserOrders(user.getName());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Restaurant> getRestaurant(EmailRequest emailRequest) {
        User user=userRepository.findByEmail(emailRequest.getEmail());
        Restaurant restaurant=restaurantRepository.findByName(user.getName());
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StatusResponse> denyOrder(StatusRequest statusRequest) {
        Order order=orderRepository.findByOrderId(statusRequest.getId());
        User user=userRepository.findByEmail(statusRequest.getEmail());
        order.setOrderStatus("Denied");
        orderRepository.save(order);
        StatusResponse statusResponse=new StatusResponse();
        statusResponse.setMessage("Order Denied");
        statusResponse.setSuccess(true);
        String emailBody = "This is to notify you that your order is Denied by restaurant";
        // sendEmail(user.getEmail(),"Order Accepted",emailBody);
        return new ResponseEntity<>(statusResponse,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<MenuResponse> updateMenu(MenuRequest menuRequest) {
        Food food=foodRepository.findFoodById(menuRequest.getId());
        if(food==null){
            MenuResponse menuResponse=new MenuResponse();
            menuResponse.setMessage("No food Item found");
            menuResponse.setSuccess(false);
            return new ResponseEntity<>(menuResponse,HttpStatus.OK);
        }
        food.setName(menuRequest.getName());
        food.setPrice(menuRequest.getPrice());
        food.setCategory(menuRequest.getCategory());
        food.setImageUrl(menuRequest.getImageUrl());
        foodRepository.save(food);
        MenuResponse menuResponse=new MenuResponse();
        menuResponse.setSuccess(true);
        menuResponse.setMessage("Updated Successfully");
        return new ResponseEntity<>(menuResponse,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Food> getFoodById(DeleteFoodRequest deleteFoodRequest) {
        Food food=foodRepository.findFoodById(deleteFoodRequest.getId());
        return new ResponseEntity<>(food,HttpStatus.OK);
    }


    private void sendEmail(String to,String subject,String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        log.info("fddf");
        javaMailSender.send(mailMessage);
    }
}
