package com.scalerassignment.hotelroommanagement.service;

import com.scalerassignment.hotelroommanagement.model.RoomType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PriceCalculator {

    private static LocalDateTime start_time;
    private static LocalDateTime end_time;



    public static LocalDateTime getStart_time() {
        return start_time;
    }

    public static void setStart_time(LocalDateTime start_time) {
        PriceCalculator.start_time = start_time;
    }

    public static LocalDateTime getEnd_time() {
        return end_time;
    }

    public static void setEnd_time(LocalDateTime end_time) {
        PriceCalculator.end_time = end_time;
    }
}
