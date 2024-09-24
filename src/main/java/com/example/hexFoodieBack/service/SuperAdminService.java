package com.example.hexFoodieBack.service;

import com.example.hexFoodieBack.entity.Restaurant;
import com.example.hexFoodieBack.request.*;
import com.example.hexFoodieBack.response.RestaurantResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SuperAdminService {

    ResponseEntity<RestaurantResponse> addRestaurant(RestaurantRequest restaurantRequest);
    ResponseEntity<RestaurantResponse> deleteRestaurant(DeleteFoodRequest deleteFoodRequest);

    ResponseEntity<RestaurantResponse> updateRestaurant(RestaurantRequest restaurantRequest);

    ResponseEntity<List<Restaurant>> displayRestaurant(LocationRequest locationRequest);

    ResponseEntity<List<Restaurant>> displaySearch(RestaurantNameRequest restaurantNameRequest);

    ResponseEntity<Restaurant> displayById(RequestId requestId);


}
