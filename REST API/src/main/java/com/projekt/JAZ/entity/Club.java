package com.projekt.JAZ.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "club")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;
    @NotNull
    @Column(name = "club_name")
    private String clubName;
    @NotNull
    @Column(name = "stadium_name")
    private String stadiumName;
    @NotNull
    @Column(name = "stadium_location")
    private String stadiumLocation;

    @JsonIgnore
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private Set<Footballer> footballer;



    @ManyToOne(optional = false)
    @JoinColumn(name = "league_id", referencedColumnName = "league_id")
    private League league;





}
