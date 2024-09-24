package com.example.hexFoodieBack.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //    private Double latitude;
    //    private Double longitude;
    private String area;
    private String zipcode;
    private String city;
    private String cuisine;
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurantId",cascade = CascadeType.ALL)
    private List<Food> foods;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    List<Order> orders;
}
