package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.Division;
import com.example.trackAdmin.Classes.Projects;
import com.example.trackAdmin.Classes.Status;
import com.example.trackAdmin.Respositories.DivisionRepository;
import com.example.trackAdmin.Respositories.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/projects")
public class ProjectsController {
    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewProject(@RequestParam String name,
                                              @RequestParam Integer divisionId) {
        Projects projectAdd = new Projects();
        projectAdd.setName(name);

        Division division = new Division();
        division.setId(divisionId);
        projectAdd.setDivision(division);

        projectsRepository.save(projectAdd);
        return "Saved";
    }


    @PutMapping("/changeDivision")
    String changeProjectDivision(@RequestParam String name,
                                 @RequestParam Integer divisionId) {
        Projects projectChange = projectsRepository.findByName(name);
        if (projectChange != null) {
            Optional<Division> divisionOptional = divisionRepository.findById(divisionId);
            if (divisionOptional.isPresent()) {
                Division division = divisionOptional.get();
                projectChange.setDivision(division);
                projectsRepository.save(projectChange);
                return "Udało się";
            } else {
                return "Podana dywizja nie istnieje";
            }
        } else {
            return "Nie udało się znaleźć projektu o podanej nazwie";
        }
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