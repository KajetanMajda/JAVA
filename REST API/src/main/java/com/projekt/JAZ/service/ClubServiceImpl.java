package com.projekt.JAZ.service;


import com.projekt.JAZ.entity.Club;
import com.projekt.JAZ.entity.League;
import com.projekt.JAZ.exception.ClubNotFoundException;
import com.projekt.JAZ.repository.ClubRepository;
import com.projekt.JAZ.repository.FootballerRepository;
import com.projekt.JAZ.repository.LeagueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class ClubServiceImpl implements ClubService {

    ClubRepository clubRepository;
    FootballerRepository footballerRepository;
    private final LeagueRepository leagueRepository;

    @Override
    public Club getClubById(Long id) {
        Optional<Club> club = clubRepository.findById(id);
        return unwrapClub(club, id);
    }

    @Override
    public List<Club> getAllClub() {
        return clubRepository.findAll();
    }

    @Override
    public Club saveClub(Club club, Long leagueId) {
        League league = LeagueServiceImpl.unwrapLeague(leagueRepository.findById(leagueId), leagueId);
        club.setLeague(league);
        return clubRepository.save(club);
    }


    @Override
    public void deleteClub(Long id) {
        clubRepository.deleteById(id);

    }



    static Club unwrapClub(Optional<Club> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new ClubNotFoundException(id);
    }
}