package com.projekt.JAZ.service;


import com.projekt.JAZ.entity.Club;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

public interface ClubService {

    Club getClubById(Long id);
    List<Club> getAllClub();

    Club saveClub(Club club, Long leagueId );
    void deleteClub(Long id);
}