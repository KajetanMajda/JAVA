package com.projekt.JAZ.service;



import com.projekt.JAZ.entity.League;
import com.projekt.JAZ.exception.LeagueNotFoundException;
import com.projekt.JAZ.repository.LeagueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LeagueServiceImpl implements LeagueService {

LeagueRepository leagueRepository;

    @Override
    public League getLeagueById(Long id) {
        Optional<League> league = leagueRepository.findById(id);
        return unwrapLeague(league, id);
    }

    @Override
    public List<League> getAllLeague() {
        return leagueRepository.findAll();
    }

    @Override
    public League saveLeague(League league) {
        return leagueRepository.save(league);
    }

    @Override
    public void deleteLeague(Long id) {
        leagueRepository.deleteById(id);

    }

    static League unwrapLeague(Optional<League> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new LeagueNotFoundException(id);
    }
}
