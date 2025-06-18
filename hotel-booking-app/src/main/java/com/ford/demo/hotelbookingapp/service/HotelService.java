package com.ford.demo.hotelbookingapp.service;

import com.ford.demo.hotelbookingapp.entities.Hotel;
import com.ford.demo.hotelbookingapp.repository.IHotelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService implements IHotelService {

    @Autowired
    private IHotelRepository hotelRepository;

    // Using @Transactional to ensure database integrity during update operations
    @Transactional
    public Hotel updateHotel(Hotel hotel) {
        // Fetch the current hotel state from the database in the same transaction
        Hotel existingHotel = hotelRepository.findById(hotel.getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Perform field updates
        existingHotel.setName(hotel.getName());
        existingHotel.setLocation(hotel.getLocation());
        existingHotel.setRating(hotel.getRating());
        existingHotel.setDescription(hotel.getDescription());
        existingHotel.setImageUrl(hotel.getImageUrl());
        existingHotel.setPricePerNight(hotel.getPricePerNight());

        // Save the updated entity, leveraging the persisted instance
        return hotelRepository.save(existingHotel);
    }


    // Adding transaction management here also ensures atomicity, especially if you have cascade operations in the entity
    @Override
    @Transactional
    public Hotel addHotel(Hotel hotel) {
        // Hotel ID should be null/avoid setting; database will generate it
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotelById(Integer id) {
        // Fetch single hotel using the repository, handling absence with exception
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
    }

    @Override
    public List<Hotel> getAllHotels() {
        // Directly return the list of hotels using findAll
        return hotelRepository.findAll();
    }

    @Override
    @Transactional
    public boolean deleteHotel(Integer id) {
        // Verify existence before attempting deletion
        if (hotelRepository.existsById(id)) {
            hotelRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
