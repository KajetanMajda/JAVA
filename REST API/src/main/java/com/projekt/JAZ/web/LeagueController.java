package com.projekt.JAZ.web;


import com.projekt.JAZ.entity.League;
import com.projekt.JAZ.service.LeagueService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/league")
public class LeagueController {
    LeagueService leagueService;

    @GetMapping("/{id}")
    public  ResponseEntity<League> getLeagueById(@PathVariable Long id){
        return new ResponseEntity<>(leagueService.getLeagueById(id),HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<League>> getLeagues() {
        return new ResponseEntity<>(leagueService.getAllLeague(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<League> saveLeague(@Valid @RequestBody League league) {
        return new ResponseEntity<>(leagueService.saveLeague(league), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteLeague(@PathVariable Long id) {
        leagueService.deleteLeague(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
