package com.example.trackAdmin.Respositories;

import com.example.trackAdmin.Classes.Status;
import com.example.trackAdmin.Classes.Users;
import org.springframework.data.repository.CrudRepository;

public interface StatusRepository extends CrudRepository<Status, Integer> {
    Status findByName(String name);
}
