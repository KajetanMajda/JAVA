package com.projekt.JAZ;

import com.projekt.JAZ.entity.Club;
import com.projekt.JAZ.entity.Footballer;
import com.projekt.JAZ.entity.League;
import com.projekt.JAZ.repository.ClubRepository;
import com.projekt.JAZ.repository.FootballerRepository;
import com.projekt.JAZ.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JazApplication {


	public static void main(String[] args) {
		SpringApplication.run(JazApplication.class, args);
	}



}
