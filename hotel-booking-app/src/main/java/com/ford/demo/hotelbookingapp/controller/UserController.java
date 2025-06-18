package com.ford.demo.hotelbookingapp.controller;

import com.ford.demo.hotelbookingapp.entities.Users;
import com.ford.demo.hotelbookingapp.service.EmailService;
import com.ford.demo.hotelbookingapp.service.RoomService;
import com.ford.demo.hotelbookingapp.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/getAvailableRooms")
    public ResponseEntity<Integer> getAvailableRooms(
            @RequestParam(required = false) String hotelName,
            @RequestParam(required = false) String checkInDate,
            @RequestParam(required = false) String roomType) {

        System.out.println("getAvailableRooms called");
        System.out.println("Hotel Name: " + hotelName);
        System.out.println("Check-In Date: " + checkInDate);
        System.out.println("Room Type: " + roomType);

        int availableRooms;

        // Check availability by room type if provided
        if (roomType != null && !roomType.isEmpty()) {
            availableRooms = roomService.getRoomsAvailableByType(hotelName, checkInDate, roomType);
            System.out.println("Available rooms of type " + roomType + ": " + availableRooms);
        } else {
            availableRooms = roomService.getRoomsAvailable(hotelName, checkInDate);
            System.out.println("Available rooms (any type): " + availableRooms);
        }

        return ResponseEntity.ok(availableRooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable int id) {
        try {
            System.out.println("getUserById called for ID: " + id);
            Users user = userService.getUserById(id);

            if (user == null) {
                System.out.println("User not found with ID: " + id);
                return ResponseEntity.notFound().build();
            }

            System.out.println("Found user: " + user.getUserName() + " with ID: " + user.getUserId());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.err.println("Error retrieving user by ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("")
    public Users addUser(@RequestBody Users user) {
        try {
            System.out.println("addUser called");
            System.out.println("Hotel Selected: " + user.getHotelSelected());
            System.out.println("Room Type Selected: " + user.getRoomTypeSelected());
            System.out.println("Check-In Date: " + user.getCheckInDate());

            // Validate booking request
            int roomsNeeded = user.getNoOfRoomsBooked();
            int availableRooms;

            // Check availability by room type if provided
            if (user.getRoomTypeSelected() != null && !user.getRoomTypeSelected().isEmpty()) {
                availableRooms = roomService.getRoomsAvailableByType(
                        user.getHotelSelected(),
                        user.getCheckInDate(),
                        user.getRoomTypeSelected()
                );
                System.out.println("Available rooms of type " + user.getRoomTypeSelected() + ": " + availableRooms);
            } else {
                availableRooms = roomService.getRoomsAvailable(user.getHotelSelected(), user.getCheckInDate());
                System.out.println("Available rooms (any type): " + availableRooms);
            }

            if (roomsNeeded > availableRooms) {
                String errorMsg = "Not enough rooms available" +
                        (user.getRoomTypeSelected() != null ? " of type " + user.getRoomTypeSelected() : "");
                throw new RuntimeException(errorMsg);
            }

            if (roomsNeeded > 3) {
                throw new RuntimeException("Can only book 3 rooms at a time");
            }

            // Process booking with room type
            if (user.getRoomTypeSelected() != null && !user.getRoomTypeSelected().isEmpty()) {
                roomService.reserveRooms(
                        user.getHotelSelected(),
                        roomsNeeded,
                        user.getCheckInDate(),
                        user.getCheckOutDate(),
                        user.getRoomTypeSelected()
                );
                System.out.println("Reserved " + roomsNeeded + " rooms of type " + user.getRoomTypeSelected());
            } else {
                roomService.reserveRooms(
                        user.getHotelSelected(),
                        roomsNeeded,
                        user.getCheckInDate(),
                        user.getCheckOutDate()
                );
                System.out.println("Reserved " + roomsNeeded + " rooms (any type)");
            }

            String rooms = roomService.roomAllocation(roomsNeeded, availableRooms);
            user.setRoomsAllocated(rooms);
            System.out.println("Room allocation: " + rooms);

            // Save user booking
            Users savedUser = userService.addUser(user);
            System.out.println("User booking saved with ID: " + savedUser.getUserId());

            // Send email asynchronously
            CompletableFuture.runAsync(() -> {
                try {
                    emailService.sendBookingConfirmationEmail(
                            savedUser.getEmail(),
                            savedUser.getUserName(),
                            savedUser.getHotelSelected(),
                            savedUser.getCheckInDate(),
                            savedUser.getCheckOutDate()
                    );
                    System.out.println("Email sent successfully to: " + savedUser.getEmail());
                } catch (MessagingException e) {
                    System.err.println("Failed to send email to " + savedUser.getEmail() + ": " + e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    System.err.println("Unexpected error during email sending: " + e.getMessage());
                    e.printStackTrace();
                }
            });

            return savedUser;
        } catch (Exception e) {
            System.err.println("Error in booking process: " + e.getMessage());
            throw e; // Re-throw to let Spring handle the error response
        }
    }

    @GetMapping("")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }
}
