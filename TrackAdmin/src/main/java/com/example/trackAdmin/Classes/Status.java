package com.example.trackAdmin.Classes;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String name;
}