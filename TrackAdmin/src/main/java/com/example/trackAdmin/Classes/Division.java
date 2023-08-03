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

    @ManyToOne
    @JoinColumn(name = "projects_id")
    private Projects projects;


}
