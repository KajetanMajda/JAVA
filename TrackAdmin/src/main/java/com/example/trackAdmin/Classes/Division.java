package com.example.trackAdmin.Classes;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    // Możesz dodać inne pola i relacje, jeśli są wymagane
}
