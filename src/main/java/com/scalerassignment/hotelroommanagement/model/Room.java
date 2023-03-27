package com.scalerassignment.hotelroommanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "floor_number")
    private int floor_number;
    @Enumerated (EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;
}
