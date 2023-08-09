package com.example.trackAdmin.Controllers;

import com.example.trackAdmin.Classes.*;
import com.example.trackAdmin.Respositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/logs")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;


    @DeleteMapping("/clearLogs")
    public String clearLogs() {
        auditLogRepository.deleteAll();
        return "redirect:/user/superAdminUser";
    }


    @GetMapping(path = "/all")
    public @ResponseBody Iterable<AuditLog> getAllElements() {
        return auditLogRepository.findAll();
    }
}
