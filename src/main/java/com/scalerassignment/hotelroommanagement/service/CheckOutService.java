package com.scalerassignment.hotelroommanagement.service;

import com.scalerassignment.hotelroommanagement.model.*;
import com.scalerassignment.hotelroommanagement.repository.BookingRepository;
import com.scalerassignment.hotelroommanagement.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.scalerassignment.hotelroommanagement.service.UtilityService.calculateRefund;
import static com.scalerassignment.hotelroommanagement.service.UtilityService.calculateRoomPrice;

@Service
public class CheckOutService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    public CheckOutService (BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    public int checkOut (long booking_id) {

        System.out.println("executing this method");

        Booking booking = bookingRepository.findById(booking_id).get();
        System.out.println("upar query chal rahi hai 1");
        List<Room> bookedRooms = booking.getRooms();
        List<Long> cancelledRoomIds = bookingRepository.findRoomsByBookedRoomStatus(booking_id);
        System.out.println("upar query chal rahi hai 2");
        List<Room> cancelledRooms = roomRepository.findRoomsById(cancelledRoomIds);
        System.out.println("upar query chal rahi hai 3");

        int bookedRoomPrice = 0, cancelledRoomPrice = 0;

        for(Room room : bookedRooms) {
            bookedRoomPrice += calculateRoomPrice(room.getRoomType(), booking.getStart_time(),booking.getEnd_time());
        }

        for (Room bookedRoom : cancelledRooms)    {
            int roomPrice = calculateRoomPrice(bookedRoom.getRoomType(), booking.getStart_time(),booking.getEnd_time());
            LocalDateTime cancellation_time;
            cancellation_time = bookingRepository.getCancellationTime(booking_id, bookedRoom.getId());

            int refund = calculateRefund(cancellation_time, booking.getStart_time(), roomPrice);
            cancelledRoomPrice += refund;
        }

        booking.setPrice((double) (bookedRoomPrice - cancelledRoomPrice));
        booking.setBookingStatus(Booking_status.COMPLETED);
        bookingRepository.completeBookingOfRoomsByBookingId(booking_id);
        bookingRepository.save(booking);

        return bookedRoomPrice - cancelledRoomPrice;
    }
}
