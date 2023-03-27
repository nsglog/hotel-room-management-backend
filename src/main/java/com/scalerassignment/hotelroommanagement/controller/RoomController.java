package com.scalerassignment.hotelroommanagement.controller;

import com.scalerassignment.hotelroommanagement.dtos.AddRoomOnExistingBookingRequestDto;
import com.scalerassignment.hotelroommanagement.dtos.CancelBookedRoomRequestDto;
import com.scalerassignment.hotelroommanagement.model.Room;
import com.scalerassignment.hotelroommanagement.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(value = "/available")
    public Set<Room> availableRooms (@RequestParam String start_time, @RequestParam String end_time) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        Set<Room> rooms = roomService.getAvailableRooms(
                LocalDateTime.parse(start_time, formatter),
                LocalDateTime.parse(end_time, formatter)
        );

        return rooms;
    }

    @PutMapping (value = "/delete")
    public void cancelBookedRoom (@RequestBody CancelBookedRoomRequestDto requestDto)    {
        roomService.cancelBookedRoom(requestDto.getBooked_room_id(), requestDto.getBeginCancellationTime());
    }

    @PutMapping(value = "/addon")
    public void addRoomOnExistingBooking (@RequestBody AddRoomOnExistingBookingRequestDto requestDto)    {
        roomService.addRoomOnExistingBooking(requestDto.getBooking_id(), requestDto.getRoom_id());
    }
}
