package com.scalerassignment.hotelroommanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "guest_email")
    private String guest_email;
    @Column(name = "start_time")
    private LocalDateTime start_time;
    @Column(name = "end_time")
    private LocalDateTime end_time;
    @Column(name = "booking_status")
    @Enumerated(EnumType.STRING)
    private Booking_status bookingStatus;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name = "booked_room",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> rooms;
    @Column(name = "total_price")
    private Double price;

}
