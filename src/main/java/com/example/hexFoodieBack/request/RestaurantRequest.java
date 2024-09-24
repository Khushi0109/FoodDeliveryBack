package com.example.hexFoodieBack.request;

import com.example.hexFoodieBack.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequest {
    private String name;
    private String cuisine;
    private String imageUrl;
//    private Double latitude;
//    private Double longitude;
    private String area;
    private String zipcode;
    private String city;
    private Long id;
}
