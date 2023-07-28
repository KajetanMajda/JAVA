package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.Division;
import com.example.trackAdmin.Classes.Projects;
import com.example.trackAdmin.Respositories.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(path = "/projects")
public class ProjectsController {
    @Autowired
    private ProjectsRepository projectsRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewProject(@RequestParam String name,
                                              @RequestParam Integer divisionId) {
        Projects project = new Projects();
        project.setName(name);

        Division division = new Division();
        division.setId(divisionId);
        project.setDivision(division);

        projectsRepository.save(project);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Projects> getAllProjects() {
        return projectsRepository.findAll();
    }
}