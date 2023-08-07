package com.example.trackAdmin.Respositories;

import com.example.trackAdmin.Classes.AuditLog;
import org.springframework.data.repository.CrudRepository;

public interface AuditLogRepository extends CrudRepository<AuditLog, Long> {
}
