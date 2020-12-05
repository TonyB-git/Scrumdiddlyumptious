package com.csc131.scrumdiddlyumptious.Entertaineon.repository;

import com.csc131.scrumdiddlyumptious.Entertaineon.models.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardsRepository extends JpaRepository<Awards, Integer>{
	
	@Query(value = "SELECT * FROM awards", nativeQuery = true)
	List<Awards> getAll();
}
