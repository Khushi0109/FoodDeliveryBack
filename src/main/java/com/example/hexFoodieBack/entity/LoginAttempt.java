package com.example.hexFoodieBack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginAttempt {
    @Id
    @GeneratedValue
    private Long id;
    private int failedAttempt;
    private Date time;

    @OneToOne
    private User user;
}
