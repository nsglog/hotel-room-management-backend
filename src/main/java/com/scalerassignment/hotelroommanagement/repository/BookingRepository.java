package com.scalerassignment.hotelroommanagement.repository;

import com.scalerassignment.hotelroommanagement.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findById (long id);

    @Query(value = "select room from " +
            "booked_room inner join booking on booking.id = booked_room.booking_id where " +
            "start_time < ?2 and end_time > ?1 and booked_room.booking_status = ?3",
    nativeQuery = true)
    Set<Room> getAllBookedRooms (LocalDateTime start, LocalDateTime end, BookedRoomStatus bookedRoomStatus);

    @Query(value = "select booked_room from " +
            "room inner join booked_room on room.id = booked_room.room_id inner join booking on booking.id = booked_room.booking_id where " +
            "booking.id = ?1",
            nativeQuery = true)
    Set<BookedRoom> getRoomTypeByBookingId (long booking_id);




    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update booking set guest_email = ?2 where id = ?1", nativeQuery = true)
    void changeGuestEmail(long bookingId, String email);


}
