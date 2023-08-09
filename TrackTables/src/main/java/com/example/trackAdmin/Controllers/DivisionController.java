package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.*;
import com.example.trackAdmin.Respositories.*;
import com.example.trackAdmin.Service.AuditLogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/division")
public class DivisionController {
    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private ElementsRepository elementsRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewDivision(@RequestParam String name,
                                               @RequestParam Integer projectsId,
                                               HttpSession session) {
        Division division = new Division();
        division.setName(name);

        Projects projects = projectsRepository.findById(projectsId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono projektu o podanym id: " + projectsId));

        division.setProjects(projects);
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        auditLogService.logAddDivision(loggedInUserEmail);
        divisionRepository.save(division);
        return "Saved";
    }


    @PutMapping("/changeProjects")
    public @ResponseBody String changeProjectDivision(@RequestParam String name,
                                                      @RequestParam Integer projectsId,
                                                      HttpSession session) {
        Division division = divisionRepository.findByName(name);
        if (division != null) {
            Projects projects = projectsRepository.findById(projectsId)
                    .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono projektu o podanym id: " + projectsId));

            division.setProjects(projects);
            String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
            auditLogService.logChangeProjectDivision(loggedInUserEmail);
            divisionRepository.save(division);
            return "Udało się";
        } else {
            return "Nie udało się znaleźć dywizji o podanej nazwie";
        }
    }


    @PutMapping("/update")
    public @ResponseBody String divisionUpdate(@RequestParam Integer id,
                                               @RequestParam String name,
                                               HttpSession session) {
        Division divisionUpdate = divisionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono projektu o podanym id: " + id));

        divisionUpdate.setName(name);
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        auditLogService.logUpdateDivision(loggedInUserEmail);
        divisionRepository.save(divisionUpdate);
        return "Udało się";
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String projectDelete(@RequestParam String name,
                                              HttpSession session) {
        Division divisionDelete = divisionRepository.findByName(name);
        if (divisionDelete != null) {
            String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
            auditLogService.logDeleteDivision(loggedInUserEmail);
            divisionRepository.delete(divisionDelete);
            return "Projekt o nazwie: " + name + " zostal usuniety";
        } else {
            return "Nie udalo sie";
        }

    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Division> getAllDivisions() {
        return divisionRepository.findAll();
    }
}