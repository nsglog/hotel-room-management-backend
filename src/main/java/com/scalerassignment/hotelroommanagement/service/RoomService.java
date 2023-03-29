package com.scalerassignment.hotelroommanagement.service;

import com.scalerassignment.hotelroommanagement.exception.CannotRemoveRoomFromBookingException;
import com.scalerassignment.hotelroommanagement.model.Booking;
import com.scalerassignment.hotelroommanagement.model.Room;
import com.scalerassignment.hotelroommanagement.repository.BookingRepository;
import com.scalerassignment.hotelroommanagement.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

    public RoomService(RoomRepository roomRepository, BookingRepository bookingRepository, BookingService bookingService) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
    }

    public List<Room> getAvailableRooms (LocalDateTime startTime, LocalDateTime endTime) {

        Set<Long> bookedRoomIds = bookingRepository.getAllBookedRooms (startTime, endTime);
        List<Room> availableRooms;
        if(bookedRoomIds.size() == 0)   {
            availableRooms = roomRepository.getAllRooms();
        }
        else {
            availableRooms = roomRepository.getAvailableRooms(bookedRoomIds);
        }
        return availableRooms;
    }

    public void cancelBookedRoom (long booking_id, long room_id, LocalDateTime beginCancellationTime) throws Exception {
        List<Room> rooms = bookingService.getRoomDetailsOfBooking(booking_id);
        if(rooms.size() == 1)   throw new CannotRemoveRoomFromBookingException("Booking must contain at least one room");
        bookingRepository.cancelRoomByBookingId(booking_id, room_id, beginCancellationTime);
    }

    public void addRoomOnExistingBooking (long booking_id, List<Long> roomIds) {

        List<Room> rooms = roomRepository.findAllById(roomIds);
        Booking booking = bookingRepository.findById(booking_id).get();

        for(Room room : rooms)  {
            booking.getRooms().add(room);
        }

        bookingRepository.saveAndFlush(booking);
    }
}
