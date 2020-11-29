package com.csc131.scrumdiddlyumptious.Entertaineon.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csc131.scrumdiddlyumptious.Entertaineon.models.Movie;
import com.csc131.scrumdiddlyumptious.Entertaineon.repository.UserRepository;

@RestController
@RequestMapping(value="/rest/")
public class MainController {
	
	@Autowired
	UserRepository userRepo;
	
	
} 	
