package com.csc131.scrumdiddlyumptious.Entertaineon.controllers;

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

@RestController
@RequestMapping(value="/rest/")
public class MainController {
	
	@Autowired
	UserRepository userRepo;

	@GetMapping(value = "/search")
	public List<Movie> movieQuery(@RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "award", required = false) String award){
		return userRepo.getMovieByQuery(year, award);
	}

	@GetMapping(value = "movies/all")
	@ResponseBody
	public List<Movie> getAll(){
		System.out.println("here");
		return userRepo.getAll();
	}
	
	@GetMapping(value = "movies/title/{title}")
	public Optional<Movie> getMovie(@PathVariable String title){
		return userRepo.getMovieByTitle(title);
	}
	
	@GetMapping(value = "movies/year/{year}")
	public List<Movie> getMovieByYear(@PathVariable int year){
		return userRepo.getMoviesByYear(year);
	}
	
	@GetMapping(value = "movies/actor/{actor}")
	public List<Movie> getMoviesByActor(@PathVariable String actor){
		return userRepo.findByActor(actor);
	}
	
} 	