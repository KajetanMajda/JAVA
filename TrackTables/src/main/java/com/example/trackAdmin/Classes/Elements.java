package com.example.trackAdmin.Classes;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
public class Elements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String transaction;
    private String description;
    private String comment;
    @Column(name = "confirm_name")
    private String confirm_name;
    private String accomplish;
    private LocalDate accomplish_date;
    private String confirm;
    private LocalDate confirm_date;
    @Column(name = "uwagi_z_realizacji")
    private String uwagi_z_realizacji;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
}
