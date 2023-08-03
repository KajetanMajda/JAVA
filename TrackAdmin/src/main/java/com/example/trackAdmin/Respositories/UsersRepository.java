package com.example.trackAdmin.Respositories;

import com.example.trackAdmin.Classes.Users;
import org.springframework.data.repository.CrudRepository;
public interface UsersRepository extends CrudRepository<Users, Integer> {


    Users findByEmail(String email);

}