package com.scalerassignment.hotelroommanagement.service;

import com.scalerassignment.hotelroommanagement.model.BookedRoom;
import com.scalerassignment.hotelroommanagement.model.Booking;
import com.scalerassignment.hotelroommanagement.model.Booking_status;
import com.scalerassignment.hotelroommanagement.model.Room;
import com.scalerassignment.hotelroommanagement.repository.BookedRoomRepository;
import com.scalerassignment.hotelroommanagement.repository.BookingRepository;
import com.scalerassignment.hotelroommanagement.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

import static com.scalerassignment.hotelroommanagement.service.UtilityService.calculateRefund;
import static com.scalerassignment.hotelroommanagement.service.UtilityService.calculateRoomPrice;

@Service
public class BookingService {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BookedRoomRepository bookedRoomRepository;
    public BookingService(RoomRepository roomRepository,
                          BookingRepository bookingRepository,
                          BookedRoomRepository bookedRoomRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.bookedRoomRepository = bookedRoomRepository;
    }

    public void createBooking (long id, Set<Long> rooms_ids, String guest_email, LocalDateTime start_time, LocalDateTime end_time) {

        Set<Room> rooms = roomRepository.findRoomsById(rooms_ids);
        Booking booking = new Booking();
        booking.setId(id);
        booking.setGuest_email(guest_email);
        booking.setStart_time(start_time);
        booking.setEnd_time(end_time);
        booking.setRooms(rooms);
        booking.setBookingStatus(Booking_status.UPCOMING);
        bookingRepository.save(booking);
    }

    public int cancelBooking (long booking_id, LocalDateTime beginCancellationTime) {
        Booking booking = bookingRepository.findById(booking_id).get();
        booking.setBookingStatus(Booking_status.CANCELLED);
        bookedRoomRepository.cancelRoomsByBookingId(beginCancellationTime, booking_id);
        Set<BookedRoom> bookedRooms = bookedRoomRepository.findBookedRoomByBookingId(booking_id);

        int totalPrice = 0;

        for (BookedRoom bookedRoom : bookedRooms) {
            Room room = bookedRoom.getRoom();
            System.out.println("booked room with room id "+bookedRoom.getId()+" "+bookedRoom.getCancellation_time());
            int room_price = calculateRoomPrice(room.getRoomType(), booking.getStart_time(), booking.getEnd_time());
            totalPrice += room_price - calculateRefund(bookedRoom.getCancellation_time(), booking.getStart_time(), room_price);
        }

        booking.setPrice((double) (totalPrice));
        bookingRepository.save(booking);
        return totalPrice;
    }

    public void updateBookingGuestEmail(long bookingId, String email) {
        bookingRepository.changeGuestEmail(bookingId, email);
    }
}
