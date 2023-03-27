package com.scalerassignment.hotelroommanagement.controller;

import com.scalerassignment.hotelroommanagement.dtos.CreateCheckoutRequestDto;
import com.scalerassignment.hotelroommanagement.service.CheckOutService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/checkout")
public class CheckOutController {

    private final CheckOutService checkOutService;

    public CheckOutController(CheckOutService checkOutService) {
        this.checkOutService = checkOutService;
    }

    @GetMapping(value = "/{id}")
    public int checkOut (@PathVariable String id) {

        Long booking_id = Long.parseLong(id);
        return checkOutService.checkOut(booking_id);
    }
}
