package com.example.hexFoodieBack.service;

import com.example.hexFoodieBack.entity.Order;
import com.example.hexFoodieBack.request.EmailRequest;
import com.example.hexFoodieBack.request.OrderRequest;
import com.example.hexFoodieBack.response.OrderResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<OrderResponse> createOrder(OrderRequest orderRequest);

    ResponseEntity<List<Order>> getOrderData(EmailRequest emailRequest);
}
