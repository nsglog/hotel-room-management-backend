package com.scalerassignment.hotelroommanagement.repository;

import com.scalerassignment.hotelroommanagement.model.BookedRoom;
import com.scalerassignment.hotelroommanagement.model.BookedRoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
@Repository
public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
    BookedRoom findBookedRoomById(long bookedRoomId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query (value = "update booked_room set booked_room_status = 'INACTIVE', cancellation_time = ?1 " +
            "where booking_id = ?2", nativeQuery = true)
    void cancelRoomsByBookingId (LocalDateTime beginCancellation, Long booking_id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query (value = "update booked_room set booked_room_status = 'COMPLETED' where booking_id = ?1", nativeQuery = true)
    void completeBookingRoomsByBookingId (Long booking_id);



    Set<BookedRoom> findBookedRoomByBookingId(long bookingId);

    @Query(value = "select * from booked_room where booking_id = ?1 and booked_room_status = 'INACTIVE'",nativeQuery = true)
    Set<BookedRoom> findRoomsByBookedRoomStatus(long bookingId, BookedRoomStatus bookedRoomStatus);
}
