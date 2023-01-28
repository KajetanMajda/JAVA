package com.projekt.JAZ.entity;

import com.projekt.JAZ.validation.ShirtNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="footballer")

public class Footballer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "footballer_id")
    private Long id;
    @NotNull
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @Column(name = "last_name")
    private String lastName;
    @NotNull
    @ShirtNumber
    @Column(name = "shirt_number")
    private int shirtNumber;

    @ManyToOne
    @JoinColumn(name = "club_id", referencedColumnName = "club_id")
    private Club club;



}