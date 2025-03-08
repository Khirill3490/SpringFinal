package org.example.finalspring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    private String roomNumber;
    private BigDecimal price;

    @Column(name = "max_occupancy")
    private Integer maxOccupancy;

    @ElementCollection
    @CollectionTable(name = "room_unavailability_dates", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "unavailability_date")
    private List<LocalDate> unavailableDates;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Booking> bookings;

}
