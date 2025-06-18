package com.ford.demo.hotelbookingapp.service;

import com.ford.demo.hotelbookingapp.entities.Room;
import com.ford.demo.hotelbookingapp.repository.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private IRoomRepository roomRepository;

    @Override
    @Transactional
    public Room addRoom(Room room) {
        // Add validation to ensure roomType is provided
        if (room.getRoomType() == null || room.getRoomType().trim().isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be empty");
        }

        // Log the room URL for debugging
        System.out.println("Adding room with URL: " + room.getRoomUrl());

        room.setStatus("Available");
        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public Room updateRoom(Room room) {
        // Add validation to ensure roomType is provided
        if (room.getRoomType() == null || room.getRoomType().trim().isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be empty");
        }

        Optional<Room> existingRoomOptional = roomRepository.findById(room.getRoomId());
        if (existingRoomOptional.isPresent()) {
            Room existingRoom = existingRoomOptional.get();

            // Debug logging for room update
            System.out.println("Updating room id: " + room.getRoomId());
            System.out.println("Updating room type to: " + room.getRoomType());
            System.out.println("Original room URL: " + existingRoom.getRoomUrl());
            System.out.println("New room URL: " + room.getRoomUrl());

            // Update basic properties
            existingRoom.setRoomNumber(room.getRoomNumber());
            existingRoom.setRoomType(room.getRoomType());
            existingRoom.setTotalCapacity(room.getTotalCapacity());
            existingRoom.setStatus(room.getStatus());
            existingRoom.setAmenities(room.getAmenities());
            existingRoom.setBookedDates(room.getBookedDates());
            existingRoom.setAvailable(room.isAvailable());

            // IMPORTANT: Only update URL if a non-empty value is provided
            if (room.getRoomUrl() != null && !room.getRoomUrl().trim().isEmpty()) {
                existingRoom.setRoomUrl(room.getRoomUrl());
                System.out.println("Setting custom URL: " + room.getRoomUrl());
            } else {
                // Keep existing URL if the new one is empty
                System.out.println("Keeping existing URL: " + existingRoom.getRoomUrl());
            }

            // Verify the URL is set before saving
            System.out.println("Room URL before saving: " + existingRoom.getRoomUrl());

            // Save and return the updated entity
            Room savedRoom = roomRepository.save(existingRoom);

            // Verify the URL after saving
            System.out.println("Room URL after saving: " + savedRoom.getRoomUrl());

            return savedRoom;
        } else {
            throw new RuntimeException("Room not found with id: " + room.getRoomId());
        }
    }

    /**
     * Method specifically for updating just the room URL
     */
    @Override
    @Transactional
    public Room updateRoomUrl(int roomId, String roomUrl) {
        System.out.println("Updating only URL for room id: " + roomId);
        System.out.println("New URL value: " + roomUrl);

        Optional<Room> existingRoomOptional = roomRepository.findById(roomId);
        if (existingRoomOptional.isPresent()) {
            Room existingRoom = existingRoomOptional.get();

            System.out.println("Original URL: " + existingRoom.getRoomUrl());

            // Set the new URL, handle null value
            if (roomUrl != null && !roomUrl.trim().isEmpty()) {
                existingRoom.setRoomUrl(roomUrl);
            }

            System.out.println("URL before saving: " + existingRoom.getRoomUrl());

            // Save and return the updated entity
            Room savedRoom = roomRepository.save(existingRoom);

            System.out.println("URL after saving: " + savedRoom.getRoomUrl());

            return savedRoom;
        } else {
            throw new RuntimeException("Room not found with id: " + roomId);
        }
    }

    @Override
    @Transactional
    public Boolean deleteRoom(int roomId) {
        if (roomRepository.existsById(roomId)) {
            roomRepository.deleteById(roomId);
            return true;
        }
        return false;
    }

    @Override
    public List<Room> getRoomsByHotelId(int hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Override
    public List<Room> getRoomsByHotelName(String hotelName) {
        return roomRepository.findByHotelName(hotelName);
    }

    @Override
    public List<Room> findAll() {
        return (List<Room>) roomRepository.findAll();
    }

    @Override
    public int getRoomsAvailable(String hotelName, String checkInDate) {
        return (int) roomRepository.findByHotelNameAndBookedDatesNotContaining(hotelName, checkInDate)
                .stream().filter(Room::isAvailable).count();
    }

    @Override
    public int getRoomsAvailableByType(String hotelName, String checkInDate, String roomType) {
        System.out.println("Checking availability for room type: " + roomType);
        return (int) roomRepository.findByHotelNameAndBookedDatesNotContainingAndRoomType(hotelName, checkInDate, roomType)
                .stream().filter(Room::isAvailable).count();
    }

    @Override
    @Transactional
    public void reserveRooms(String hotelName, int roomsNeeded, String checkInDate, String checkOutDate) {
        // Call the new method with null roomType for backward compatibility
        reserveRooms(hotelName, roomsNeeded, checkInDate, checkOutDate, null);
    }

    @Override
    @Transactional
    public void reserveRooms(String hotelName, int roomsNeeded, String checkInDate, String checkOutDate, String roomType) {
        List<Room> availableRooms;

        // Filter by room type if provided
        if (roomType != null && !roomType.isEmpty()) {
            System.out.println("Reserving " + roomsNeeded + " rooms of type: " + roomType);
            availableRooms = roomRepository.findByHotelNameAndBookedDatesNotContainingAndRoomType(hotelName, checkInDate, roomType)
                    .stream().filter(room -> "Available".equalsIgnoreCase(room.getStatus())).toList();
        } else {
            System.out.println("Reserving " + roomsNeeded + " rooms (any type)");
            availableRooms = roomRepository.findByHotelNameAndBookedDatesNotContaining(hotelName, checkInDate)
                    .stream().filter(room -> "Available".equalsIgnoreCase(room.getStatus())).toList();
        }

        System.out.println("Found " + availableRooms.size() + " available rooms");

        if (availableRooms.size() >= roomsNeeded) {
            for (int i = 0; i < roomsNeeded; i++) {
                Room room = availableRooms.get(i);
                room.initializeBookingStatus(); // Ensure bookedDates is initialized
                room.getBookedDates().add(checkInDate);
                room.getBookedDates().add(checkOutDate);
                room.setStatus("Booked");
                Room savedRoom = roomRepository.save(room);
                System.out.println("Reserved room ID: " + savedRoom.getRoomId() + ", Type: " + savedRoom.getRoomType());
            }
        } else {
            throw new RuntimeException("Not enough rooms available" +
                    (roomType != null && !roomType.isEmpty() ? " of type: " + roomType : ""));
        }
    }

    @Override
    public String roomAllocation(int roomsNeeded, int availableRooms) {
        if (roomsNeeded > availableRooms) {
            return "Not enough rooms available";
        }
        StringBuilder allocation = new StringBuilder();
        for (int i = 1; i <= roomsNeeded; i++) {
            allocation.append("Room ").append(i).append(", ");
        }
        if (!allocation.isEmpty()) {
            allocation.setLength(allocation.length() - 2);
        }
        return allocation.toString();
    }

    @Override
    public Room getRoomByHotelIdAndRoomId(int hotelId, int roomId) {
        Optional<Room> roomOpt = roomRepository.findByHotelIdAndRoomId(hotelId, roomId);
        return roomOpt.orElse(null);
    }

    @Override
    public int getTotalRooms(String hotelName) {
        return roomRepository.findByHotelName(hotelName).size();
    }
}
