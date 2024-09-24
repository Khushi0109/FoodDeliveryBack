package com.example.hexFoodieBack.service;

import com.example.hexFoodieBack.entity.Restaurant;
import com.example.hexFoodieBack.repository.LocationRespository;
import com.example.hexFoodieBack.repository.RestaurantRepository;
import com.example.hexFoodieBack.request.*;
import com.example.hexFoodieBack.response.RestaurantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SuperAdminServiceImple implements SuperAdminService{
    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    LocationRespository locationRespository;



    @Override
    public ResponseEntity<RestaurantResponse> addRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant=restaurantRepository.findByName(restaurantRequest.getName());
        if(restaurant==null){
            Restaurant restaurant1=new Restaurant();
            restaurant1.setName(restaurantRequest.getName());
            restaurant1.setCuisine(restaurantRequest.getCuisine());
            restaurant1.setImageUrl(restaurantRequest.getImageUrl());
//            restaurant1.setLatitude(restaurantRequest.getLatitude());
//            restaurant1.setLongitude(restaurantRequest.getLongitude());
            restaurant1.setArea(restaurantRequest.getArea());
            restaurant1.setZipcode(restaurantRequest.getZipcode());
            restaurant1.setCity(restaurantRequest.getCity());
            restaurantRepository.save(restaurant1);
            RestaurantResponse restaurantResponse=new RestaurantResponse();
            restaurantResponse.setMessage("Restaurant Added Successfully");
            restaurantResponse.setSuccess(true);
            return new ResponseEntity<>(restaurantResponse, HttpStatus.OK);
        }
        else {
            RestaurantResponse restaurantResponse=new RestaurantResponse();
            restaurantResponse.setMessage("Restaurant is already present");
            restaurantResponse.setSuccess(false);
            return new ResponseEntity<>(restaurantResponse,HttpStatus.OK);
        }
    }

    public ResponseEntity<RestaurantResponse> deleteRestaurant(DeleteFoodRequest deleteFoodRequest){
        Restaurant restaurant=restaurantRepository.findByRestaurantId(deleteFoodRequest.getId());
        if(restaurant==null){
            RestaurantResponse restaurantResponse=new RestaurantResponse();
            restaurantResponse.setSuccess(false);
            restaurantResponse.setMessage("No Restaurant Found with the given name");
            return new ResponseEntity<>(restaurantResponse,HttpStatus.OK);
        }
        restaurantRepository.delete(restaurant);
        RestaurantResponse restaurantResponse=new RestaurantResponse();
        restaurantResponse.setMessage("Restaurant Deleted Successfully");
        restaurantResponse.setSuccess(true);
        return new ResponseEntity<>(restaurantResponse,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RestaurantResponse> updateRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant=restaurantRepository.findByRestaurantId(restaurantRequest.getId());
        if(restaurant==null){
            RestaurantResponse restaurantResponse=new RestaurantResponse();
            restaurantResponse.setSuccess(false);
            restaurantResponse.setMessage("No Restaurant Found with the given name");
            return new ResponseEntity<>(restaurantResponse,HttpStatus.OK);
        }
        restaurant.setName(restaurantRequest.getName());
        restaurant.setImageUrl(restaurantRequest.getImageUrl());
        restaurant.setCuisine(restaurantRequest.getCuisine());
        restaurant.setArea(restaurantRequest.getArea());
        restaurant.setZipcode(restaurantRequest.getZipcode());
        restaurant.setCity(restaurantRequest.getCity());
        restaurantRepository.save(restaurant);
        RestaurantResponse restaurantResponse=new RestaurantResponse();
        restaurantResponse.setSuccess(true);
        restaurantResponse.setMessage("Updated Successfully");
        return new ResponseEntity<>(restaurantResponse,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Restaurant>> displayRestaurant(LocationRequest locationRequest) {
        List<Restaurant> restaurants=restaurantRepository.findByLocation(locationRequest.getArea());
        return new ResponseEntity<>(restaurants,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Restaurant>> displaySearch(RestaurantNameRequest restaurantNameRequest) {
        List<Restaurant> restaurants=restaurantRepository.findByNameOrFood(restaurantNameRequest.getName());
        return new ResponseEntity<>(restaurants,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Restaurant> displayById(RequestId requestId) {
        Restaurant restaurant=restaurantRepository.findByRestaurantId(requestId.getId());
        return new ResponseEntity<>(restaurant,HttpStatus.OK);

    }

//    public ResponseEntity<List<Restaurant>> findNearByRestaurant(NearByRequest nearByRequest){
//        return new ResponseEntity<>(restaurantRepository.findByDistance(nearByRequest),HttpStatus.OK);
//    }

}
