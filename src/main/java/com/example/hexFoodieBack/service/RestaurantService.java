package com.example.hexFoodieBack.service;


import com.example.hexFoodieBack.entity.Food;
import com.example.hexFoodieBack.entity.Order;
import com.example.hexFoodieBack.entity.Restaurant;
import com.example.hexFoodieBack.request.*;
import com.example.hexFoodieBack.response.MenuResponse;
import com.example.hexFoodieBack.response.StatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RestaurantService {
    ResponseEntity<MenuResponse> addMenuItems(MenuRequest menuRequest);

    ResponseEntity<MenuResponse> deleteMenuItems(DeleteFoodRequest deleteFoodRequest);

    ResponseEntity<List<Food>> displayMenu(RestaurantIdRequest restaurantIdRequest);

    ResponseEntity<List<Food>> getMyMenu(EmailRequest emailRequest);

    ResponseEntity<StatusResponse> orderState(StatusRequest statusRequest);

    ResponseEntity<List<Order>> getUserOrders(EmailRequest emailRequest);

    ResponseEntity<Restaurant> getRestaurant(EmailRequest emailRequest);

    ResponseEntity<StatusResponse> denyOrder(StatusRequest statusRequest);

    ResponseEntity<MenuResponse> updateMenu(MenuRequest menuRequest);

    ResponseEntity<Food> getFoodById(DeleteFoodRequest deleteFoodRequest);

}
