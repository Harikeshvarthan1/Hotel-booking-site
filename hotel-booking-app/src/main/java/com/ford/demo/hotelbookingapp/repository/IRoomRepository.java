package com.ford.demo.hotelbookingapp.repository;// IRoomRepository.java
import com.ford.demo.hotelbookingapp.entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoomRepository extends CrudRepository<Room, Integer> {

    List<Room> findByHotelName(String hotelName);

    List<Room> findByIsAvailableTrue();

    List<Room> findByRoomTypeAndIsAvailableTrue(String roomType);

    List<Room> findByHotelNameAndBookedDatesNotContainingAndRoomType(String hotelName, String date, String roomType);

    List<Room> findByHotelNameAndBookedDatesNotContaining(String hotelName, String date);

    List<Room> findByHotelId(int hotelId);

    Optional<Room> findByHotelIdAndRoomId(int hotelId, int roomId); // New method
}
