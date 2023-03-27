package com.scalerassignment.hotelroommanagement.service;

import com.scalerassignment.hotelroommanagement.model.BookedRoom;
import com.scalerassignment.hotelroommanagement.model.BookedRoomStatus;
import com.scalerassignment.hotelroommanagement.model.Booking;
import com.scalerassignment.hotelroommanagement.model.Room;
import com.scalerassignment.hotelroommanagement.repository.BookedRoomRepository;
import com.scalerassignment.hotelroommanagement.repository.BookingRepository;
import com.scalerassignment.hotelroommanagement.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookedRoomRepository bookedRoomRepository;
    private final BookingRepository bookingRepository;

    public RoomService(RoomRepository roomRepository, BookedRoomRepository bookedRoomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookedRoomRepository = bookedRoomRepository;
        this.bookingRepository = bookingRepository;
    }

    public Set<Room> getAvailableRooms (LocalDateTime startTime, LocalDateTime endTime) {

        System.out.println(startTime+" start time and end time "+endTime);
        Set<Room> bookedRooms = bookingRepository.getAllBookedRooms (startTime, endTime, BookedRoomStatus.ACTIVE);
        Set<Room> availableRooms = roomRepository.getAvailableRooms(bookedRooms);
        return availableRooms;
    }

    public void cancelBookedRoom (long booked_room_id, LocalDateTime beginCancellationTime) {

        System.out.println(beginCancellationTime);

        BookedRoom bookedRoom = bookedRoomRepository.findBookedRoomById(booked_room_id);
        bookedRoom.setBooked_room_status(BookedRoomStatus.INACTIVE);
        bookedRoom.setCancellation_time(beginCancellationTime);
        bookedRoomRepository.save(bookedRoom);
    }

    public void addRoomOnExistingBooking (long booking_id, long room_id) {

        System.out.println(room_id);

        Room room = roomRepository.findById(room_id).get();
        System.out.println(room_id);
        Booking booking = bookingRepository.findById(booking_id).get();
        booking.getRooms().add(room);
        bookingRepository.save(booking);
    }
}
