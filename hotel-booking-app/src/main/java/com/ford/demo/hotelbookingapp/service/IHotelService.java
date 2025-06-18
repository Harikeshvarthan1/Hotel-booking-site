package com.ford.demo.hotelbookingapp.service;

import com.ford.demo.hotelbookingapp.entities.Hotel;

import java.util.List;

public interface IHotelService {
    Hotel addHotel(Hotel hotel);
    Hotel getHotelById(Integer id);
    List<Hotel> getAllHotels();
    Hotel updateHotel(Hotel hotel);
    boolean deleteHotel(Integer id);
}
