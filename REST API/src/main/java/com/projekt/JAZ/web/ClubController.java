package com.projekt.JAZ.web;

import com.projekt.JAZ.entity.Club;
import com.projekt.JAZ.service.ClubService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/club")
public class ClubController {

    ClubService clubService;

    @GetMapping("/{id}")
    public  ResponseEntity<Club> getClubById(@PathVariable Long id){
        return new ResponseEntity<>(clubService.getClubById(id),HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Club>> getClubs() {
        return new ResponseEntity<>(clubService.getAllClub(), HttpStatus.OK);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Club> deleteCLub(Long id){
        clubService.deleteClub(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/save/{leagueId}")
    public ResponseEntity<Club> saveClub(@Valid @RequestBody Club club, @PathVariable Long leagueId) {
        return new ResponseEntity<>(clubService.saveClub(club,leagueId), HttpStatus.CREATED);
    }










}
