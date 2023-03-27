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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

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



    @GetMapping(value = "/bookings")
    public List<Booking> getAllBookings () {
        return bookingRepository.findAll();
    }

    @PostMapping(value = "/book")
    public void createBooking (@RequestBody CreateBookingRequestDto requestDto) {

        bookingService.createBooking(requestDto.getId(),
                requestDto.getRoomIds(),
                requestDto.getGuest_email(),
                requestDto.getStartTime(),
                requestDto.getEndTime());
    }



    @DeleteMapping(value = "/booking")
    public int deleteBooking (@RequestBody CancelBookingRequestDto requestDto) {
        return bookingService.cancelBooking(requestDto.getBooking_id(), requestDto.getBeginCancellationTime());
    }

    @PutMapping(value = "/email")
    public void changeGuestEmail (@RequestBody ChangeUserEmailRequestDto requestDto) {
        bookingService.updateBookingGuestEmail(requestDto.getBooking_id(), requestDto.getEmail());
    }
}
