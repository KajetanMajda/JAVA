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

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteStatus(@RequestParam String name){
        Status deleteStatus = statusRepository.findByName(name);
        if(deleteStatus != null){
            statusRepository.delete(deleteStatus);
            return "Status o nazwie: " + name + " zostal usuniety";
        }else{
            return "Nie udalo sie";
        }

    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateStatus(@RequestParam Integer id,
                                             @RequestParam(required = false) String name){
        Status updateStatus = statusRepository.findById(id).orElse(null);
         if(updateStatus != null){
             updateStatus.setName(name);
             return "Udalo sie";
         }else {
             return "Nie udalo sie";
         }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Status> getAllDivisions() {
        return statusRepository.findAll();
    }
}