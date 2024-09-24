package com.example.hexFoodieBack.controller;

import com.example.hexFoodieBack.entity.Order;
import com.example.hexFoodieBack.request.EmailRequest;
import com.example.hexFoodieBack.request.OrderRequest;
import com.example.hexFoodieBack.response.OrderResponse;
import com.example.hexFoodieBack.service.OrderServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class OrderController {
    @Autowired
    OrderServiceImple orderServiceImple;

    @PostMapping("/createorder")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        return orderServiceImple.createOrder(orderRequest);
    }
    @PostMapping("/getorderdata")
    public ResponseEntity<List<Order>> getOrderData(@RequestBody EmailRequest emailRequest){
        return orderServiceImple.getOrderData(emailRequest);
    }
}
