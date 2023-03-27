package com.scalerassignment.hotelroommanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "booked_room")
public class BookedRoom {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    @Column(name = "booked_room_status")
    @Enumerated(EnumType.STRING)
    private BookedRoomStatus booked_room_status;
    @Column(name = "cancellation_time")
    private LocalDateTime cancellation_time;
}
