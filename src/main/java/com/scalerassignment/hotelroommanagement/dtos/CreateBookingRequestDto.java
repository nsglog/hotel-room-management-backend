package com.scalerassignment.hotelroommanagement.dtos;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class CreateBookingRequestDto {
    private long id;
    private Set<Long> roomIds;
    private String guest_email;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
