package com.ford.demo.hotelbookingapp.repository;

import com.ford.demo.hotelbookingapp.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelRepository extends JpaRepository<Hotel, Integer> {
}
