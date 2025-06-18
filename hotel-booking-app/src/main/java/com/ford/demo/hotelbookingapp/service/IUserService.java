package com.ford.demo.hotelbookingapp.service;

import com.ford.demo.hotelbookingapp.entities.Users;

import java.util.List;

public interface IUserService {
    Users addUser(Users user);
    Users getUserById(int id);
    Users updateUser(Users user);
    boolean deleteUserById(int id);

    List<Users> getAllUsers();
}
