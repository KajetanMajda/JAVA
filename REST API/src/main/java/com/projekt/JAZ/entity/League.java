package com.projekt.JAZ.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="league")

public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "league_id")
    private Long id;
    @NotNull
    @Column(name= "league_name")
    private String leagueName;
    @NotNull
    @Column(name= "country_of_origin")
    private String countryOfOrigin;


    @JsonIgnore
    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private Set<Club> club;


}
