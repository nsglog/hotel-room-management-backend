package com.scalerassignment.hotelroommanagement.service;

import com.scalerassignment.hotelroommanagement.model.Booking;
import com.scalerassignment.hotelroommanagement.model.Booking_status;
import com.scalerassignment.hotelroommanagement.model.Room;
import com.scalerassignment.hotelroommanagement.repository.BookingRepository;
import com.scalerassignment.hotelroommanagement.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.scalerassignment.hotelroommanagement.service.UtilityService.calculateRefund;
import static com.scalerassignment.hotelroommanagement.service.UtilityService.calculateRoomPrice;

@Service
public class BookingService {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    private static long primary_key = 1;

    public BookingService(RoomRepository roomRepository,
                          BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public void createBooking (List<Room> rooms, String guest_email, LocalDateTime start_time, LocalDateTime end_time) {

        Booking booking = new Booking();
        booking.setId(primary_key);
        booking.setGuest_email(guest_email);
        booking.setStart_time(start_time);
        booking.setEnd_time(end_time);
        booking.setRooms(rooms);
        booking.setBookingStatus(Booking_status.UPCOMING);
        bookingRepository.save(booking);
        primary_key++;
    }

    public int cancelBooking (long booking_id, LocalDateTime beginCancellationTime) {

        Booking booking = bookingRepository.findById(booking_id).get();

        bookingRepository.cancelRoomsByBookingId(beginCancellationTime, booking_id);

        List<Long> roomIds = bookingRepository.getRoomByBookingId(booking_id);
        List<Room> bookedRooms = roomRepository.findRoomsById(roomIds);

        int totalPrice = 0;

        for (Room room : bookedRooms) {
            int booking_price = calculateRoomPrice(room.getRoomType(), booking.getStart_time(), booking.getEnd_time());
            totalPrice += booking_price - calculateRefund(beginCancellationTime, booking.getStart_time(), booking_price);
        }

        System.out.println(totalPrice);
        bookingRepository.updateBookingData(totalPrice, booking_id);

        return totalPrice;
    }

    public void updateBookingGuestEmail(long bookingId, String email) {
        bookingRepository.changeGuestEmail(bookingId, email);
    }

    public List<Room> getRoomDetailsOfBooking(long booking_id) {
        List<Long>  roomIds = bookingRepository.getRoomByBookingId(booking_id);
        return roomRepository.findRoomsById(roomIds);
    }
}
