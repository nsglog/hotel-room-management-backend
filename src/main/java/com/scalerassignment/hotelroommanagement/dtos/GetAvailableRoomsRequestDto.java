package com.scalerassignment.hotelroommanagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetAvailableRoomsRequestDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
