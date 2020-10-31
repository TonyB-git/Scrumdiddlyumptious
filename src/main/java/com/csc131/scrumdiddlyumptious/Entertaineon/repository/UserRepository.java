package com.csc131.scrumdiddlyumptious.Entertaineon.repository;

import com.csc131.scrumdiddlyumptious.Entertaineon.models.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Movie, Integer> {

}
