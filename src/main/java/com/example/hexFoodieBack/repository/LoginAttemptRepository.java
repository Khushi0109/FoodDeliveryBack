package com.example.hexFoodieBack.repository;

import com.example.hexFoodieBack.entity.LoginAttempt;
import com.example.hexFoodieBack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt,Long> {
    @Query("Select u from LoginAttempt u Where u.user=:user")
    public LoginAttempt findByUserId(@Param("user") User user);
}
