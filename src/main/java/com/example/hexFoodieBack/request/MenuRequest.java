package com.example.hexFoodieBack.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {
    private String name;
    private String category;
    private Integer price;
    private String imageUrl;
    private String restaurantName;
    private Long id;
}
