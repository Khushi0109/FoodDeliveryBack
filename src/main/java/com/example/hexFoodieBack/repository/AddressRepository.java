package com.example.hexFoodieBack.repository;

import com.example.hexFoodieBack.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
