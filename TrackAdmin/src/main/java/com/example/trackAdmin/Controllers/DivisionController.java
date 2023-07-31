package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.Division;
import com.example.trackAdmin.Respositories.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/division")
public class DivisionController {
    @Autowired
    private DivisionRepository divisionRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewDivision(@RequestParam String name) {
        Division division = new Division();
        division.setName(name);
        divisionRepository.save(division);
        return "Saved";
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteDivision(@RequestParam String name){
        Division divisionDelete = divisionRepository.findByName(name);
        if(divisionDelete != null){
            divisionRepository.delete(divisionDelete);
            return name + " zostalo usuiniete";
        }else {
            return name + " nie istnieje";
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateDivision9(@RequestParam Integer id,
                                                @RequestParam String name){
        Division updateDivision = divisionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie odnaleziono dzialu o padanym id: " + id));

        updateDivision.setName(name);
        divisionRepository.save(updateDivision);
        return "Udalo sie";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Division> getAllDivisions() {
        return divisionRepository.findAll();
    }
}