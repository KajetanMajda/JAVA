package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.*;
import com.example.trackAdmin.Respositories.*;
import com.example.trackAdmin.Service.AuditLogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/projects")
public class ProjectsController {

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private ElementsRepository elementsRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private StatusRepository statusRepository;


    @PostMapping(path = "/add")
    public @ResponseBody String addNewProject(@RequestParam String name,
                                              HttpSession session) {
        Projects projectAdd = new Projects();
        projectAdd.setName(name);
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        auditLogService.logAddProject(loggedInUserEmail);
        projectsRepository.save(projectAdd);
        return "Saved";
    }


    @PutMapping("/update")
    public @ResponseBody String projectsUpdate(@RequestParam Integer id,
                                               @RequestParam String name,
                                               HttpSession session) {
        Projects projectUpdate = projectsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono projektu o podanym id: " + id));

        projectUpdate.setName(name);
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        auditLogService.logUpdateProject(loggedInUserEmail);
        projectsRepository.save(projectUpdate);
        return "Udało się";
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String projectDelete(@RequestParam String name,
                                              HttpSession session) {
        Projects projectDelete = projectsRepository.findByName(name);
        if (projectDelete != null) {
            String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
            auditLogService.logDeleteProject(loggedInUserEmail);
            projectsRepository.delete(projectDelete);
            return "Projekt o nazwie: " + name + " zostal usuniety";
        } else {
            return "Nie udalo sie";
        }

    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Projects> getAllProjects() {
        return projectsRepository.findAll();
    }


    @PostMapping(path = "/copy")
    public ResponseEntity<String> copyProject(
            @RequestParam (name = "sourceProjectId") Integer id,
            @RequestParam Integer targetProjectId) {

        try {
            Projects sourceProject = projectsRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono projektu źródłowego"));

            Projects targetProject = projectsRepository.findById(targetProjectId)
                    .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono projektu docelowego"));

            List<Division> sourceDivisions = divisionRepository.findByProjects_Id(id);

            for (Division sourceDivision : sourceDivisions) {
                Division newDivision = new Division();
                newDivision.setName(sourceDivision.getName());
                newDivision.setProjects(targetProject);

                divisionRepository.save(newDivision);

                List<Elements> sourceElements = elementsRepository.findByDivisionId(sourceDivision.getId());

                for (Elements sourceElement : sourceElements) {
                    Elements newElement = new Elements();
                    newElement.setTransaction(sourceElement.getTransaction());

                    Status statusToSet = statusRepository.findById(5)
                            .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono statusu o podanym id: 5"));

                    newElement.setDivision(newDivision);
                    newElement.setStatus(statusToSet);
                    elementsRepository.save(newElement);
                }
            }

            return ResponseEntity.ok("Projekt został skopiowany wraz z dywizjami i elementami.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd kopiowania projektu: " + e.getMessage());
        }
    }
}