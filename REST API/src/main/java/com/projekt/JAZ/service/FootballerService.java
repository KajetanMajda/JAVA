package com.projekt.JAZ.service;


import com.projekt.JAZ.entity.Club;
import com.projekt.JAZ.entity.Footballer;
import java.util.List;


public interface FootballerService {
    List<Footballer> getAllFootballers();

    Footballer getFootballerById(Long id);

    Footballer saveFootballer(Footballer footballer, Long clubId);

    void deleteFootballer(Long id);



}
