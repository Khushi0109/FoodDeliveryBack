package com.example.hexFoodieBack.controller;

import com.example.hexFoodieBack.entity.Order;
import com.example.hexFoodieBack.request.DeliveryOrderRequest;
import com.example.hexFoodieBack.request.EmailRequest;
import com.example.hexFoodieBack.request.StatusRequest;
import com.example.hexFoodieBack.response.StatusResponse;
import com.example.hexFoodieBack.service.DeliveryServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class DeliveryController {

    @Autowired
    DeliveryServiceImple deliveryServiceImple;

    @PostMapping("/deliverystate")
    public ResponseEntity<StatusResponse> deliveryState(@RequestBody StatusRequest statusRequest){
        return deliveryServiceImple.DeliveryState(statusRequest);
    }

    @PostMapping("/getdeliveryorders")
    public ResponseEntity<List<Order>> getDeliveryOrders(@RequestBody DeliveryOrderRequest deliveryOrderRequest){
        return deliveryServiceImple.getdeliveryOrders(deliveryOrderRequest);
    }
    @PostMapping("/getpickedorders")
    public ResponseEntity<List<Order>> getPickedOrders(@RequestBody EmailRequest emailRequest){

        return deliveryServiceImple.getPickedOrders(emailRequest);
    }
    @PostMapping("/delivered")
    public ResponseEntity<StatusResponse> markDelivered(@RequestBody StatusRequest statusRequest){
        return deliveryServiceImple.markDelivered(statusRequest);
    }
}
