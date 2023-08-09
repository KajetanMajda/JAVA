package com.example.trackAdmin.Service;
import com.example.trackAdmin.Classes.AuditLog;
import com.example.trackAdmin.Respositories.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void logUserLogin(String email) {
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("Logged in");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    //elements

    public void logElementUpdate(String email, Integer elementId) {
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdatedElementID: " + elementId);
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    public void logElementsAdd(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("AddedElementID");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }


    public void logDeleteElement(String email, Integer elementId) {
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("DeletedElementID: " + elementId);
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }



    public void logCopyElement(String email) {
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("CopyElement");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    //users

    public void logUserAdd(String email) {
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("CreatedUser");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    public void logUpdateRole(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdatedRoleUser");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }
    public void logUpdateUser(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdatedUser");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }
    public void logDeleteUser(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("DeleteUser");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    //roles

    public void logAddRole(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("AddRole");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    public void logDeleteRole(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("DeleteRole");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }
    public void logUpdataRole(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdateRole");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    //statuses


    public void logAddStatus(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("AddStatus");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    public void logDeleteStatus(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("DeleteStatus");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }
    public void logUpdateStatus(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdateStatus");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    //projects


    public void logAddProject(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("AddProject");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    public void logDeleteProject(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("DeleteProject");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }
    public void logUpdateProject(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdateProject");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    //divisions

    public void logAddDivision(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("AddDivision");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    public void logDeleteDivision(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("DeleteDivision");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }
    public void logUpdateDivision(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdateDivision");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }
    public void logChangeProjectDivision(String email){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdateProjectDivision");
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }
}