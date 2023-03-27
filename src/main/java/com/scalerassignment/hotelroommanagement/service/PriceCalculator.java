package com.scalerassignment.hotelroommanagement.service;

import com.scalerassignment.hotelroommanagement.model.RoomType;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculator {

    public static int roomPrice (RoomType roomType) {

        if(roomType.equals(RoomType.DELUXE))    {
            return 50;
        }
        else if (roomType.equals(RoomType.EXECUTIVE))   {
            return 80;
        }
        else {
            return 100;
        }
    }
}
