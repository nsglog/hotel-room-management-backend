package com.scalerassignment.hotelroommanagement.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRoomOnExistingBookingRequestDto {

    private long booking_id;
    private long room_id;
}
