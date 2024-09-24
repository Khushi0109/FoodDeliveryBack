package com.example.hexFoodieBack.repository;

import com.example.hexFoodieBack.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {

    @Query("Select f from Food f Where f.id=:id")
    Food findFoodById(@Param("id") Long id);
    @Query("Select f from Food f Where f.name=:name")
    Food findFoodByName(@Param("name") String name);
    @Query("Select f from Food f Where f.restaurantId.id=:restaurantId")
    List<Food> findByRestaurant(@Param("restaurantId") Long restaurantId);

    @Query("Select f from Food f Where f.restaurantId.name=:name")
    List<Food> findByRestaurantName(@Param("name") String name);
}
