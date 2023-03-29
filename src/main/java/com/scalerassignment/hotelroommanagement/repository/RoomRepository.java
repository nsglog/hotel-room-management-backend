package com.scalerassignment.hotelroommanagement.repository;

import com.scalerassignment.hotelroommanagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


    @Query(value = "select * from room where id in (:roomIds)", nativeQuery = true)
    List<Room> findRoomsById(@Param(value = "roomIds") List<Long> roomIds);
    @Query(value = "select * from room where id not in(:roomIds)", nativeQuery = true)
    List<Room> getAvailableRooms (@Param(value = "roomIds") Set<Long> roomIds);

    @Query(value = "select * from room", nativeQuery = true)
    List<Room> getAllRooms();
}
