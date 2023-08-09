package com.example.trackAdmin.Respositories;

import com.example.trackAdmin.Classes.Elements;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ElementsRepository extends CrudRepository<Elements, Integer> {
    List<Elements> findByDivisionId(Integer divisionId);
}
