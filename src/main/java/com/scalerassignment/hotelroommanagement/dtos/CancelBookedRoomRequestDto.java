package com.scalerassignment.hotelroommanagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CancelBookedRoomRequestDto {
    private long booked_room_id;
    private LocalDateTime beginCancellationTime;
}
