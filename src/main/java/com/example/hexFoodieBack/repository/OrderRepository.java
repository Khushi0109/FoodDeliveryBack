package com.example.hexFoodieBack.repository;

import com.example.hexFoodieBack.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("Select o from Order o Where o.user.email=:email")
    List<Order> findByUserEmail(@Param("email") String email);

    @Query("Select o from Order o Where o.id=:id")
    Order findByOrderId(@Param("id") Long id);
    @Query("Select o from Order o Where o.restaurant.area=:area And o.orderStatus='Confirmed'")
    List<Order> getDeliveryOrders(@Param("area") String area);

    @Query("Select o from Order o Where o.restaurant.name=:name And (o.orderStatus='Placed' Or o.orderStatus='Confirmed' Or o.orderStatus='Picked Up' Or o.orderStatus='Delivered')")
    List<Order> getUserOrders(@Param("name") String name);

    @Query("Select o from Order o Where o.delivery.email=:email And (o.orderStatus='Picked Up' Or o.orderStatus='Delivered')")
    List<Order> getPickedOrders(@Param("email") String email);
}
