package com.example.hexFoodieBack.entity;

import com.example.hexFoodieBack.entity.enums.ERoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String email;
    private String mobileNumber;
    private String name;
    private  String password;

    @Column(columnDefinition="boolean default false")
    private Boolean locked=false;

    private Date failedTime;

    @OneToMany(mappedBy = "address_user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Address> address;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order>orders;

    private String role;

    @OneToMany(mappedBy = "delivery",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order>ordersD;

//    @Enumerated(EnumType.STRING)
//    private ERoles role;
}
