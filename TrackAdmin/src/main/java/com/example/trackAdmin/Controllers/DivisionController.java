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

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Division> getAllDivisions() {
        return divisionRepository.findAll();
    }
}