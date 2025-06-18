package com.ford.demo.hotelbookingapp.controller;

import com.ford.demo.hotelbookingapp.entities.Room;
import com.ford.demo.hotelbookingapp.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @PostMapping("")
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        System.out.println("Received request to add room with URL: " + room.getRoomUrl());
        Room createdRoom = roomService.addRoom(room);
        System.out.println("Created room with URL: " + createdRoom.getRoomUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable int roomId, @RequestBody Room room) {
        try {
            // Set the roomId for the update operation
            room.setRoomId(roomId);

            // Log the URL before updating
            System.out.println("Controller: Updating room " + roomId + " with URL: " + room.getRoomUrl());

            Room updatedRoom = roomService.updateRoom(room);
            System.out.println("Controller: Room updated, returning URL: " + updatedRoom.getRoomUrl());

            return ResponseEntity.ok(updatedRoom);
        } catch (RuntimeException ex) {
            System.out.println("Error updating room: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("")
    public List<Room> getAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/{hotelId}")
    public List<Room> getRoomsByHotelId(@PathVariable int hotelId) {
        return roomService.getRoomsByHotelId(hotelId);
    }

    @GetMapping("/{hotelId}/{roomId}")
    public ResponseEntity<Room> getRoomByHotelIdAndRoomId(@PathVariable int hotelId, @PathVariable int roomId) {
        Room room = roomService.getRoomByHotelIdAndRoomId(hotelId, roomId);
        if (room != null) {
            return ResponseEntity.ok(room);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Changed to PUT to match the Angular service call
    @PutMapping("/{roomId}/update-url")
    public ResponseEntity<Room> updateRoomUrl(
            @PathVariable int roomId,
            @RequestBody Map<String, String> payload) {

        String roomUrl = payload.get("roomUrl");
        System.out.println("PUT request to update room URL for roomId: " + roomId);
        System.out.println("New URL from request: " + roomUrl);

        try {
            Room updatedRoom = roomService.updateRoomUrl(roomId, roomUrl);
            System.out.println("URL update successful, returning: " + updatedRoom.getRoomUrl());
            return ResponseEntity.ok(updatedRoom);
        } catch (RuntimeException ex) {
            System.out.println("Error updating room URL: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer roomId) {
        if (roomService.deleteRoom(roomId)) {
            return ResponseEntity.noContent().build();  // HTTP 204
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
