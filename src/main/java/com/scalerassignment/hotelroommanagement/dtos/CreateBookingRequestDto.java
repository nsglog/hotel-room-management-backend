package com.scalerassignment.hotelroommanagement.dtos;


import com.scalerassignment.hotelroommanagement.model.Room;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CreateBookingRequestDto {
    private Set<Room> rooms;
    private String guest_email;
    private LocalDateTime start_time;
    private LocalDateTime end_time;

}
