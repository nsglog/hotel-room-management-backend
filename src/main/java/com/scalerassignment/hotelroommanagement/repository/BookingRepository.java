package com.scalerassignment.hotelroommanagement.repository;

import com.scalerassignment.hotelroommanagement.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findById (long id);

    @Query(value = "select booked_room.room_id from " +
            "booked_room inner join booking on booked_room.booking_id = booking.id where " +
            "booking.start_time < ?2 and booking.end_time > ?1 and booked_room.booked_room_status = 'ACTIVE'",
    nativeQuery = true)
    Set<Long> getAllBookedRooms (LocalDateTime start, LocalDateTime end);

    @Query(value = "select booked_room.room_id from " +
            "booked_room inner join booking on booking.id = booked_room.booking_id where " +
            "booking.id = ?1 and booked_room.booked_room_status = 'ACTIVE'",
            nativeQuery = true)
    List<Long> getRoomByBookingId (long booking_id);




    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update booking set guest_email = ?2 where id = ?1", nativeQuery = true)
    void changeGuestEmail(long bookingId, String email);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value ="update booked_room set booked_room.booked_room_status = 'INACTIVE', booked_room.cancellation_time = ?1 " +
            "where booked_room.booking_id = ?2", nativeQuery = true)
    void cancelRoomsByBookingId(LocalDateTime beginCancellationTime, long bookingId);

    @Query(value = "select cancellation_time from booked_room where booking_id = ?1 and room_id = ?2", nativeQuery = true)
    LocalDateTime getCancellationTime(long booking_id, long room_id);

    @Query(value = "select booked_room.room_id from booked_room inner join booking on booked_room.booking_id = booking.id where " +
            "booking.id = ?1 and booked_room.booked_room_status = 'INACTIVE'", nativeQuery = true)
    List<Long> findRoomsByBookedRoomStatus(long bookingId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update booked_room set booked_room.booked_room_status = 'COMPLETED' where booked_room.booking_id = ?1",
            nativeQuery = true)
    void completeBookingOfRoomsByBookingId(long bookingId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update booked_room set booked_room.cancellation_time = ?3, booked_room.booked_room_status = 'INACTIVE' " +
            "where booking_id = ?1 and room_id = ?2 ", nativeQuery = true)
    void cancelRoomByBookingId(long bookingId, long roomId, LocalDateTime beginCancellationTime);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update booking set total_price = ?1, booking_status = 'CANCELLED' where id = ?2", nativeQuery = true)
    void updateBookingData(double totalPrice, long booking_id);


    @Query(value = "select * from booking where booking_status = 'UPCOMING'", nativeQuery = true)
    List<Booking> getAllBookings();

    @Query(value = "select room_id from booked_room where " +
            "booked_room_status = 'INACTIVE' and booking_id = ?1", nativeQuery = true)
    List<Long> getCancelledRoomIdsOfCurrentBooking(long bookingId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update booked_room set booked_room_status = 'ACTIVE', cancellation_time = NULL where " +
            "booking_id = :booking_id and room_id in (:roomIds)", nativeQuery = true)
    void updateExistingRoomInCurrentBooking (@Param(value = "booking_id") long booking_id, @Param(value = "roomIds") List<Long> roomIds);

}
