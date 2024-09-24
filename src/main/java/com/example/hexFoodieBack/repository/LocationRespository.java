package com.example.hexFoodieBack.repository;

import com.example.hexFoodieBack.entity.Location;
import com.example.hexFoodieBack.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRespository extends JpaRepository<Location,Long> {

}
