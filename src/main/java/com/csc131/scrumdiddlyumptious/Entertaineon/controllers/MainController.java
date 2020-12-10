package com.csc131.scrumdiddlyumptious.Entertaineon.controllers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csc131.scrumdiddlyumptious.Entertaineon.models.*;
import com.csc131.scrumdiddlyumptious.Entertaineon.repository.UserRepository;
import com.csc131.scrumdiddlyumptious.Entertaineon.repository.PersonRepository;
import com.csc131.scrumdiddlyumptious.Entertaineon.repository.AwardsRepository;

@RestController
@RequestMapping(value="/rest/")
public class MainController<movieRepo> {
	
	
	
	@Autowired
	UserRepository movieRepo;
	
	@Autowired 
	PersonRepository personRepo;
	
	@Autowired 
	AwardsRepository awardsRepo;

	@GetMapping(value = "/search")
	public List<Movie> movieQuery(@RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "award", required = false) String award){
		return movieRepo.getMovieByQuery(year, award);
	}

	@GetMapping(value = "movies/all")
	@ResponseBody
	public List<Movie> getAll(){
		System.out.println("here");
		return movieRepo.getAll();
	}
	
	@GetMapping(value = "movies/title/{title}")
	public List<Movie> getMovie(@PathVariable String title){
		String result = java.net.URLDecoder.decode(title, StandardCharsets.UTF_8);
		return movieRepo.getMovieByTitle(result);
	}
	
	@GetMapping(value = "movies/year/{year}")
	public List<Movie> getMovieByYear(@PathVariable int year){
		return movieRepo.getMoviesByYear(year);
	}
	
	@GetMapping(value = "movies/actor/{actor}")
	public List<Movie> getMoviesByActor(@PathVariable String actor){
		return movieRepo.findByActor(actor);
	}
	
	@GetMapping(value = "/actors/all")
	public List<Person> getAllActors(){
		return personRepo.getAll();
	}
	
	@GetMapping(value = "/awards/all")
	public List<Awards> getAllAwards(){
		return awardsRepo.getAll();
	}

	@GetMapping(value = "movies/award/{award}")
	public List<Movie> getMoviesByAwards(@PathVariable String award){
		return movieRepo.findByAward(award);
	}
	@GetMapping(value = "movies/genre/{genre}")
	public List<Movie> getMoviesByGenre(@PathVariable String genre){
		return movieRepo.findByGenre(genre);
	}
} 	