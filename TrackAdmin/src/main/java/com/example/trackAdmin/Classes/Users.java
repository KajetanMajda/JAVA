package com.example.trackAdmin.Classes;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "UsersRole_id")
    private UsersRole role;
}