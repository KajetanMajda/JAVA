package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.Status;
import com.example.trackAdmin.Respositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/status")
public class StatusController {
    @Autowired
    private StatusRepository statusRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewDivision(@RequestParam String name) {
        Status status = new Status();
        status.setName(name);
        statusRepository.save(status);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Status> getAllDivisions() {
        return statusRepository.findAll();
    }
}