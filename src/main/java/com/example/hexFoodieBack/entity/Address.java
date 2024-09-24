package com.example.hexFoodieBack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String City;
    private String state;
    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User address_user;
}
