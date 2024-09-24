package com.example.hexFoodieBack.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;
    private String email;
    private Long id;
}
