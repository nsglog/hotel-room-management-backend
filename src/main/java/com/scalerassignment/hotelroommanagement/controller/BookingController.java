package com.scalerassignment.hotelroommanagement.controller;

import com.scalerassignment.hotelroommanagement.dtos.*;
import com.scalerassignment.hotelroommanagement.model.Booking;
import com.scalerassignment.hotelroommanagement.model.Room;
import com.scalerassignment.hotelroommanagement.repository.BookingRepository;
import com.scalerassignment.hotelroommanagement.service.BookingService;
import com.scalerassignment.hotelroommanagement.service.CheckOutService;
import com.scalerassignment.hotelroommanagement.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;
    private final RoomService roomService;
    private final BookingRepository bookingRepository;
    private final CheckOutService checkOutService;
    public BookingController(BookingService bookingService, RoomService roomService, BookingRepository bookingRepository, CheckOutService checkOutService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.bookingRepository = bookingRepository;
        this.checkOutService = checkOutService;
    }

    @GetMapping("/")
    public List<Booking> getAllBookings () {
        return bookingRepository.getAllBookings();
    }

    @PostMapping(value = "/")
    public void createBooking (@RequestBody CreateBookingRequestDto requestDto) {

        for (Room room : requestDto.getRooms()) System.out.println("room type : " + room.getRoomType());

        bookingService.createBooking(
                requestDto.getRooms(),
                requestDto.getGuest_email(),
                requestDto.getStart_time(),
                requestDto.getEnd_time());
    }

    @DeleteMapping(value = "/{id}")
    public int removeExistingBooking (@PathVariable String id) {
        return bookingService.cancelBooking( Long.parseLong(id),
                LocalDateTime.now());
    }

    @PutMapping(value = "/{id}/email")
    public void changeGuestEmail (@PathVariable String id, @RequestBody ChangeUserEmailRequestDto requestDto) {
        bookingService.updateBookingGuestEmail(Long.parseLong(id), requestDto.getEmail());
    }

    @PutMapping (value = "/{booking_id}/booked-rooms/cancel-room/{room_id}")
    public void cancelBookedRoomOnExistingBooking (@PathVariable(name = "booking_id") String booking_id,
                                                   @PathVariable(name = "room_id") String room_id)    {

        System.out.println(room_id+" <- booking id , room_id -> "+room_id);

        try {
            roomService.cancelBookedRoom(Long.parseLong(booking_id),
                    Long.parseLong(room_id),
                    LocalDateTime.now());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(value = "/{id}/booked-rooms/add-room")
        public void addRoomOnExistingBooking (@PathVariable String id, @RequestBody AddRoomOnExistingBookingRequestDto requestDto)    {
        roomService.addRoomOnExistingBooking(Long.parseLong(id), requestDto.getRoomIds());
    }
    @PutMapping(value = "/{id}/checkout")
    public int checkOut (@PathVariable String id) {
        Long booking_id = Long.parseLong(id);
        return checkOutService.checkOut(booking_id);
    }

    @GetMapping(value = "/{id}/room-details")
    public List<Room> getRoomDetailsOfBooking (@PathVariable String id) {
        return bookingService.getRoomDetailsOfBooking(Long.parseLong(id));
    }
}
