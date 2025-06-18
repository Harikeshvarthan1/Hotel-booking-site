package com.ford.demo.hotelbookingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    private String roomNumber;
    private String roomType;
    private int totalCapacity;
    private int hotelId;
    private boolean isAvailable;
    private String hotelName;
    private String status;

    @Column(length = 255, columnDefinition = "TEXT")
    private String roomUrl;

    @ElementCollection
    private List<String> amenities; // e.g., WiFi, AC, TV

    @ElementCollection
    private List<String> bookedDates; // Dates for which the room is booked

    // Constructor not using roomId when creating a new entity
    public Room(String roomNumber, String roomType, int totalCapacity, int hotelId, boolean isAvailable,
                String hotelName, String status, List<String> amenities, List<String> bookedDates) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.totalCapacity = totalCapacity;
        this.hotelId = hotelId;
        this.isAvailable = isAvailable;
        this.hotelName = hotelName;
        this.status = status;
        this.amenities = amenities != null ? amenities : new ArrayList<>();
        this.bookedDates = bookedDates != null ? bookedDates : new ArrayList<>();
        this.roomUrl = generateRoomUrl(hotelId, roomNumber); // Default URL
    }

    // Additional constructor that accepts the roomUrl parameter
    public Room(String roomNumber, String roomType, int totalCapacity, int hotelId, boolean isAvailable,
                String hotelName, String status, String roomUrl, List<String> amenities, List<String> bookedDates) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.totalCapacity = totalCapacity;
        this.hotelId = hotelId;
        this.isAvailable = isAvailable;
        this.hotelName = hotelName;
        this.status = status;
        this.amenities = amenities != null ? amenities : new ArrayList<>();
        this.bookedDates = bookedDates != null ? bookedDates : new ArrayList<>();
        // Only use default URL if roomUrl is null or empty
        this.roomUrl = roomUrl != null && !roomUrl.isEmpty() ? roomUrl : generateRoomUrl(hotelId, roomNumber);
    }

    // Custom logic to generate URL
    private String generateRoomUrl(int hotelId, String roomNumber) {
        return "https://example.com/rooms/hotel" + hotelId + "/" + roomNumber + ".jpg"; // Default URL pattern
    }

    public void initializeBookingStatus() {
        if (bookedDates == null) {
            bookedDates = new ArrayList<>(); // Initialize the list if it's null
        }
    }

    public boolean isAvailable() {
        return "Available".equalsIgnoreCase(this.status);
    }

    // IMPORTANT: Remove the problematic setRoomId method that was overriding the custom URL
    // Let Lombok generate a simple setter that doesn't modify other fields

    // Optional: You can override the setRoomUrl method for additional logging if needed
    public void setRoomUrl(String roomUrl) {
        System.out.println("Setting room URL to: " + roomUrl);
        this.roomUrl = roomUrl;
    }
}
