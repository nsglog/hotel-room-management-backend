package com.scalerassignment.hotelroommanagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CancelBookingRequestDto {
    private Long booking_id;
    private LocalDateTime beginCancellationTime;
}
