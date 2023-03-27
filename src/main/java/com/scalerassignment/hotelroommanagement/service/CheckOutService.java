package com.scalerassignment.hotelroommanagement.service;

import com.scalerassignment.hotelroommanagement.model.*;
import com.scalerassignment.hotelroommanagement.repository.BookedRoomRepository;
import com.scalerassignment.hotelroommanagement.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.scalerassignment.hotelroommanagement.service.UtilityService.calculateRefund;
import static com.scalerassignment.hotelroommanagement.service.UtilityService.calculateRoomPrice;

@Service
public class CheckOutService {

    private final BookingRepository bookingRepository;
    private final BookedRoomRepository bookedRoomRepository;
    public CheckOutService (BookingRepository bookingRepository, BookedRoomRepository bookedRoomRepository) {
        this.bookingRepository = bookingRepository;
        this.bookedRoomRepository = bookedRoomRepository;
    }

    public int checkOut (long booking_id) {

        Booking booking = bookingRepository.findById(booking_id).get();
        Set<Room> bookedRooms = booking.getRooms();
        Set<BookedRoom> cancelledRooms = bookedRoomRepository.findRoomsByBookedRoomStatus(booking_id, BookedRoomStatus.INACTIVE);

        int bookedRoomPrice = 0, cancelledRoomPrice = 0;

        for(Room room : bookedRooms) {
            bookedRoomPrice += calculateRoomPrice(room.getRoomType(), booking.getStart_time(),booking.getEnd_time());
        }

        for (BookedRoom bookedRoom : cancelledRooms)    {
            Room room = bookedRoom.getRoom();
            int roomPrice = calculateRoomPrice(room.getRoomType(), booking.getStart_time(),booking.getEnd_time());
            int refund = calculateRefund(bookedRoom.getCancellation_time(), booking.getStart_time(), roomPrice);
            cancelledRoomPrice += refund;
        }

        booking.setPrice((double) (bookedRoomPrice - cancelledRoomPrice));
        booking.setBookingStatus(Booking_status.COMPLETED);
        bookedRoomRepository.completeBookingRoomsByBookingId(booking_id);
        bookingRepository.save(booking);

        return bookedRoomPrice - cancelledRoomPrice;
    }
}
