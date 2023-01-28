package com.projekt.JAZ.service;

import com.projekt.JAZ.entity.Club;
import com.projekt.JAZ.entity.Footballer;
import com.projekt.JAZ.entity.League;
import com.projekt.JAZ.exception.FootballerNotFoundException;
import com.projekt.JAZ.repository.ClubRepository;
import com.projekt.JAZ.repository.FootballerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FootballerServiceImpl implements FootballerService {

    FootballerRepository footballerRepository;
    private final ClubRepository clubRepository;

    @Override
    public Footballer getFootballerById(Long id) {
        Optional<Footballer> footballer = footballerRepository.findById(id);
        return unwrapFootballer(footballer, id);
    }
    @Override
    public List<Footballer> getAllFootballers() {
        return  footballerRepository.findAll();
    }


    @Override
    public Footballer saveFootballer(Footballer footballer, Long clubId) {

        Club club = ClubServiceImpl.unwrapClub(clubRepository.findById(clubId), clubId);
        footballer.setClub(club);
        return footballerRepository.save(footballer);
    }

    @Override
    public void deleteFootballer(Long id) {
        footballerRepository.deleteById(id);

    }

    static Footballer unwrapFootballer(Optional<Footballer> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new FootballerNotFoundException(id);
    }

}
