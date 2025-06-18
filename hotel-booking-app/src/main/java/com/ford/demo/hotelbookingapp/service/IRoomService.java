package com.ford.demo.hotelbookingapp.service;

import com.ford.demo.hotelbookingapp.entities.Room;
import java.util.List;

public interface IRoomService {
    Room addRoom(Room room);
    Room updateRoom(Room room);
    Room updateRoomUrl(int roomId, String roomUrl);
    Boolean deleteRoom(int roomId);
    List<Room> getRoomsByHotelId(int hotelId);
    List<Room> getRoomsByHotelName(String hotelName);
    List<Room> findAll();
    int getRoomsAvailable(String hotelName, String checkInDate);
    int getRoomsAvailableByType(String hotelName, String checkInDate, String roomType);
    void reserveRooms(String hotelName, int roomsNeeded, String checkInDate, String checkOutDate);
    void reserveRooms(String hotelName, int roomsNeeded, String checkInDate, String checkOutDate, String roomType);
    String roomAllocation(int roomsNeeded, int availableRooms);
    Room getRoomByHotelIdAndRoomId(int hotelId, int roomId);
    int getTotalRooms(String hotelName);
}
