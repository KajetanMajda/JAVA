package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.Division;
import com.example.trackAdmin.Classes.Projects;
import com.example.trackAdmin.Respositories.DivisionRepository;
import com.example.trackAdmin.Respositories.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/division")
public class DivisionController {
    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewDivision(@RequestParam String name,
                                               @RequestParam Integer projectsId) {
        Division division = new Division();
        division.setName(name);

        Projects projects = projectsRepository.findById(projectsId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono projektu o podanym id: " + projectsId));

        division.setProjects(projects);

        divisionRepository.save(division);
        return "Saved";
    }


    @PutMapping("/changeProjects")
    public @ResponseBody String changeProjectDivision(@RequestParam String name,
                                                      @RequestParam Integer projectsId) {
        Division division = divisionRepository.findByName(name);
        if (division != null) {
            Projects projects = projectsRepository.findById(projectsId)
                    .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono projektu o podanym id: " + projectsId));

            division.setProjects(projects);
            divisionRepository.save(division);
            return "Udało się";
        } else {
            return "Nie udało się znaleźć dywizji o podanej nazwie";
        }
    }



    @PutMapping("/update")
    public @ResponseBody String divisionUpdate(@RequestParam Integer id,
                                               @RequestParam String name){
        Division divisionUpdate = divisionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono projektu o podanym id: " + id));

        divisionUpdate.setName(name);
        divisionRepository.save(divisionUpdate);
        return "Udało się";
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String projectDelete(@RequestParam String name){
        Division divisionDelete = divisionRepository.findByName(name);
        if(divisionDelete != null){
            divisionRepository.delete(divisionDelete);
            return "Projekt o nazwie: " + name + " zostal usuniety";
        }else{
            return "Nie udalo sie";
        }

    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Division> getAllDivisions() {
        return divisionRepository.findAll();
    }
}