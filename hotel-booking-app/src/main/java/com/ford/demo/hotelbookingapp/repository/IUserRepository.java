package com.ford.demo.hotelbookingapp.repository;

import com.ford.demo.hotelbookingapp.entities.Users; // Correcting the import path to your new Users entity
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<Users, Integer> {
}
