package com.example.trackAdmin.Respositories;

import com.example.trackAdmin.Classes.Division;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DivisionRepository extends CrudRepository<Division, Integer> {
    Division findByName(String name);

    List<Division> findByProjects_Id(Integer projectId);
}
