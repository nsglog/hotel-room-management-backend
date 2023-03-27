package com.scalerassignment.hotelroommanagement.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserEmailRequestDto {
    private long booking_id;
    private String email;
}
