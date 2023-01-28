package com.projekt.JAZ.web;

import com.projekt.JAZ.entity.Club;
import com.projekt.JAZ.entity.Footballer;
import com.projekt.JAZ.service.FootballerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/footballer")
public class FootballerController {

    FootballerService footballerService;


    @GetMapping("/{id}")
    public ResponseEntity<Footballer> getFootballerById(@PathVariable Long id) {
        return new ResponseEntity<>(footballerService.getFootballerById(id),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Footballer>> getFootballers() {
        return new ResponseEntity<>(footballerService.getAllFootballers(), HttpStatus.OK);
    }

    @PostMapping("save/{clubId}")
    public ResponseEntity<Footballer> saveFootballer(@Valid @RequestBody Footballer footballer, @PathVariable Long clubId) {
        return new ResponseEntity<>(footballerService.saveFootballer(footballer, clubId), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFootballer(@PathVariable Long id) {
        footballerService.deleteFootballer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
