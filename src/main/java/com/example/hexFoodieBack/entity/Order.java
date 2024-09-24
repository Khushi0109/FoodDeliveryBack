package com.example.hexFoodieBack.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
//    @JoinColumn(name = "usersId")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;

    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;

    @OneToOne
    private Address address;

    @ManyToOne
    private Restaurant restaurant;

    private Integer totalItems;
    private Integer totalPrice;
    private String orderStatus;

    @ManyToOne
    @JsonIgnore
    private User delivery;
}
