package com.projekt.JAZ.service;



import com.projekt.JAZ.entity.League;

import java.util.List;


public interface LeagueService {

    League getLeagueById(Long id);
    List<League> getAllLeague();

    League saveLeague(League league);

    void deleteLeague(Long id);

}