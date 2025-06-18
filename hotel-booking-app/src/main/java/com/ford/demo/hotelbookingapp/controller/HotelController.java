package com.ford.demo.hotelbookingapp.controller;

import com.ford.demo.hotelbookingapp.entities.Hotel;
import com.ford.demo.hotelbookingapp.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    @Autowired
    private IHotelService hotelService;

    @PostMapping("")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
        // Hotel id will be generated automatically by the database.
        Hotel createdHotel = hotelService.addHotel(hotel);
        return ResponseEntity.ok(createdHotel);
    }

    @GetMapping("")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Integer id) {
        Hotel hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable int id, @RequestBody Hotel hotel) {
        try {
            hotel.setId(id);
            Hotel updatedHotel = hotelService.updateHotel(hotel);
            // Return the hotel object directly, not a success message
            return ResponseEntity.ok(updatedHotel);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id) {
        if (hotelService.deleteHotel(id)) {
            return "Hotel successfully deleted";
        }
        return "Hotel Not Found";
    }
}
