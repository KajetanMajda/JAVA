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

//    public void logUserLogin(String email) {
//        AuditLog log = new AuditLog();
//        log.setEmail(email);
//        log.setAction("Logged in");
//        log.setDateAndTime(LocalDateTime.now());
//        auditLogRepository.save(log);
//    }

//    public void logUserLogout(String email) {
//        AuditLog auditLog = new AuditLog();
//        auditLog.setEmail(email);
//        auditLog.setAction(" logged out");
//        auditLog.setDateAndTime(LocalDateTime.now());
//        auditLogRepository.save(auditLog);
//    }

    public void logElementUpdate(String email, Integer elementId) {
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdatedElementID: " + elementId);
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    public void logUserAdd(String email, String name, String surname) {
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("CreatedUser: " + name + " " + surname);
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    public void logUpdateRole(String email, String name, String surname){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdatedRole: " + name + " " + surname);
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }
    public void logUpdateUser(String email, String name, String surname){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdatedUser: " + name + " " + surname);
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }
    public void logDeleteUser(String email, String name, String surname){
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setAction("UpdatedUser: " + name + " " + surname);
        log.setDateAndTime(LocalDateTime.now());
        auditLogRepository.save(log);
    }



}
