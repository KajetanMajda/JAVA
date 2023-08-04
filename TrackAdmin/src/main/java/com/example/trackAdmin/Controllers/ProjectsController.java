package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.Division;
import com.example.trackAdmin.Classes.Projects;
import com.example.trackAdmin.Respositories.DivisionRepository;
import com.example.trackAdmin.Respositories.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/projects")
public class ProjectsController {

    @Autowired
    private ProjectsRepository projectsRepository;


    @PostMapping(path = "/add")
    public @ResponseBody String addNewProject(@RequestParam String name) {
        Projects projectAdd = new Projects();
        projectAdd.setName(name);

        projectsRepository.save(projectAdd);
        return "Saved";
    }


    @PutMapping("/update")
    public @ResponseBody String projectsUpdate(@RequestParam Integer id,
                                               @RequestParam String name){
        Projects projectUpdate = projectsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono projektu o podanym id: " + id));

        projectUpdate.setName(name);
        projectsRepository.save(projectUpdate);
        return "Udało się";
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String projectDelete(@RequestParam String name){
        Projects projectDelete = projectsRepository.findByName(name);
        if(projectDelete != null){
            projectsRepository.delete(projectDelete);
            return "Projekt o nazwie: " + name + " zostal usuniety";
        }else{
            return "Nie udalo sie";
        }

    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Projects> getAllProjects() {
        return projectsRepository.findAll();
    }
}