package com.scalerassignment.hotelroommanagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddRoomOnExistingBookingRequestDto {
    private List<Long> roomIds;
}
