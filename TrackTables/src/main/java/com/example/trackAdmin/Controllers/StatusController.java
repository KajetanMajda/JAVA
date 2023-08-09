package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.Status;
import com.example.trackAdmin.Respositories.StatusRepository;
import com.example.trackAdmin.Service.AuditLogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping(path = "/status")
public class StatusController {
    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewDivision(@RequestParam String name,
                                               HttpSession session) {
        Status status = new Status();
        status.setName(name);
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
        auditLogService.logAddStatus(loggedInUserEmail);
        statusRepository.save(status);
        return "Saved";
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteStatus(@RequestParam String name,
                                             HttpSession session){
        Status deleteStatus = statusRepository.findByName(name);
        if(deleteStatus != null){
            String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
            auditLogService.logDeleteStatus(loggedInUserEmail);
            statusRepository.delete(deleteStatus);
            return "Status o nazwie: " + name + " zostal usuniety";
        }else{
            return "Nie udalo sie";
        }

    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateStatus(@RequestParam Integer id,
                                             @RequestParam(required = false) String name,
                                             HttpSession session){
        Status updateStatus = statusRepository.findById(id).orElse(null);
         if(updateStatus != null){
             updateStatus.setName(name);
             String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
             auditLogService.logUpdateStatus(loggedInUserEmail);
             statusRepository.save(updateStatus);
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