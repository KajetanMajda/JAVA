package com.example.trackAdmin.Respositories;

import com.example.trackAdmin.Classes.UsersRole;
import org.springframework.data.repository.CrudRepository;

public interface UsersRoleRepository extends CrudRepository<UsersRole, Integer> {
    UsersRole findByName(String roleName);
}
