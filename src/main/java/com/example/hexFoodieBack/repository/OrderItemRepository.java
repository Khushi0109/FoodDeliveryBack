package com.example.hexFoodieBack.repository;

import com.example.hexFoodieBack.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItems,Long> {

}
