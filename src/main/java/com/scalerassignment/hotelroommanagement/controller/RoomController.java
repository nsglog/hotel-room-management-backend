package com.scalerassignment.hotelroommanagement.controller;

import com.scalerassignment.hotelroommanagement.model.Room;
import com.scalerassignment.hotelroommanagement.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    public List<Room> availableRooms (@RequestParam(name = "start_time") String start_time,
                                      @RequestParam(name = "end_time") String end_time) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        List<Room> rooms = roomService.getAvailableRooms(
                LocalDateTime.parse(start_time, formatter),
                LocalDateTime.parse(end_time, formatter)
        );

        return rooms;
    }
}
