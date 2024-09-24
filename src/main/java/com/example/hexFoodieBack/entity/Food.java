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
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private String category;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurantId;

   @JsonIgnore
    @OneToMany(mappedBy = "food",cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;

}
