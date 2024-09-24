package com.example.hexFoodieBack.service;

import com.example.hexFoodieBack.entity.Order;
import com.example.hexFoodieBack.request.DeliveryOrderRequest;
import com.example.hexFoodieBack.request.EmailRequest;
import com.example.hexFoodieBack.request.StatusRequest;
import com.example.hexFoodieBack.response.StatusResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DeliveryService {
    ResponseEntity<StatusResponse> DeliveryState(StatusRequest statusRequest);

    ResponseEntity<List<Order>> getdeliveryOrders(DeliveryOrderRequest deliveryOrderRequest);

    ResponseEntity<List<Order>> getPickedOrders(EmailRequest emailRequest);

    ResponseEntity<StatusResponse> markDelivered(StatusRequest statusRequest);
}
