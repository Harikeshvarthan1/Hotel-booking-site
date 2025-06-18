package com.ford.demo.hotelbookingapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String userName;
    private String email;
    private String Aadhaar;
    private String hotelSelected;
    private String roomTypeSelected; // Changed from roomtype to roomTypeSelected for consistency
    private String phoneNumber;
    private String checkInDate;
    private String checkOutDate;
    private int noOfRoomsBooked;
    private String roomsAllocated;

    // Constructor with null checks
    public Users(int userId,String userName, String email, String Aadhaar, String hotelSelected,
                 String roomTypeSelected, String phoneNumber, String checkInDate,
                 String checkOutDate, int noOfRoomsBooked, String roomsAllocated) {
        if (userName == null) {
            throw new IllegalArgumentException("User name cannot be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (checkInDate == null) {
            throw new IllegalArgumentException("Check-in date cannot be null");
        }
        if (checkOutDate == null) {
            throw new IllegalArgumentException("Check-out date cannot be null");
        }
        if (Aadhaar == null) {
            throw new IllegalArgumentException("Aadharid cannot be null");
        }
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.Aadhaar = Aadhaar;
        this.hotelSelected = hotelSelected;
        this.roomTypeSelected = roomTypeSelected;
        this.phoneNumber = phoneNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.noOfRoomsBooked = noOfRoomsBooked;
        this.roomsAllocated = roomsAllocated;
    }

    // Setter methods with validation
    public void setUserName(String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("User name cannot be null");
        }
        this.userName = userName;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        this.email = email;
    }

    public void setCheckInDate(String checkInDate) {
        if (checkInDate == null) {
            throw new IllegalArgumentException("Check-in date cannot be null");
        }
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        if (checkOutDate == null) {
            throw new IllegalArgumentException("Check-out date cannot be null");
        }
        this.checkOutDate = checkOutDate;
    }

    // Add explicit setter for roomTypeSelected with validation
    public void setRoomTypeSelected(String roomTypeSelected) {
        // Allow null/empty for backward compatibility, but log it
        if (roomTypeSelected == null || roomTypeSelected.trim().isEmpty()) {
            System.out.println("Warning: Empty room type provided");
        }
        this.roomTypeSelected = roomTypeSelected;
    }
}
