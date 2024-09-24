package com.example.hexFoodieBack.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnore
    private User user;

    @OneToOne
    private Restaurant restaurant;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    @JsonIgnore
    @Column(name = "cart_items")
    private Set<CartItems> cartItems;

    private Integer totalPrice;
    private Integer totalItems;
}
