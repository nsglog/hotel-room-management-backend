package com.scalerassignment.hotelroommanagement.service;

import com.scalerassignment.hotelroommanagement.model.RoomType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class UtilityService {

    public static int calculateRoomPrice (RoomType roomType, LocalDateTime start_time, LocalDateTime end_time) {

        int room_price_per_hour = roomPrice(roomType);
        double effectiveTime = (end_time.toEpochSecond(ZoneOffset.UTC) - start_time.toEpochSecond(ZoneOffset.UTC)) / 3600.0;
        return room_price_per_hour * (int)effectiveTime;
    }

    public static int calculateRefund (LocalDateTime beginCancellation,
                                 LocalDateTime booking_startTime,
                                 double price) {
        // todo calculate refund here after a booking is completely
        long hourDiff = ChronoUnit.HOURS.between(beginCancellation, booking_startTime);

        System.out.println("hour diff is : "+hourDiff);

        if(hourDiff >= 48)
            return (int)price;
        else if (hourDiff >= 24 && hourDiff < 48)
            return (int)(price * 0.5);
        else if (hourDiff < 24)
            return 0;
        return 0;
    }

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
