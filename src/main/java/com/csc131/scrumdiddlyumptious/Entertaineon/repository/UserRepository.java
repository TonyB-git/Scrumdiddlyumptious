package com.csc131.scrumdiddlyumptious.Entertaineon.repository;

import com.csc131.scrumdiddlyumptious.Entertaineon.models.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Movie, Integer> {
	
	/* 
	 *V1 of the query search parameter. Right now it only returns all of the nominations & winner of a specified award for a specified year
	 *
	 *An example of this being used would be  https://localhost:8080/rest/?year=1940&award=writing
	*/
	@Query(value = "SELECT t1.* "
			 + "FROM movies t1, people_awards t2, awards t3 "
			 + "WHERE (t1.year = :year AND t3.type = :award AND t2.movie_id = t1.movie_id AND t3.award_id = t2.award_id);", nativeQuery = true)
	List<Movie> getMovieByQuery(Integer year, String award);	
	
	
	// Returns every movie in the movie table with all of its associated columns 
	@Query(value = "SELECT * FROM movies", nativeQuery = true)
	List<Movie> getAll();
	
	
	// Returns the specified movie by its title and all of its associated columns in the movie table
	@Query(value = "SELECT * FROM movies WHERE title = ?1", nativeQuery = true)
	List<Movie> getMovieByTitle(String title);
	
	
	// Returns every award winning film from the specified year
	@Query(value = "SELECT * FROM movies WHERE year = ?1", nativeQuery = true)
	List<Movie> getMoviesByYear(int year);
	
	
	// Returns every film that a specified actor has been in where 
	// they either were nominated or received an award
	@Query(value = "SELECT t1.* "  
			+ "FROM movies t1, people_awards t2, people t3 "  
			+ "WHERE (t1.movie_id = t2.movie_id AND t2.person_id = t3.person_id AND t3.name = ?1);", nativeQuery = true)
	List<Movie> findByActor(String actor);
	
	@Query(value = "SELECT t1.* "  
			+ "FROM movies t1, people_awards t2, awards t3 "  
			+ "WHERE (t1.movie_id = t2.movie_id AND t2.award_id = t3.award_id AND t3.type = ?1);", nativeQuery = true)
	List<Movie> findByAward(String award);
	
	@Query(value = "SELECT * FROM movies WHERE genre = ?1", nativeQuery = true)
	List<Movie> findByGenre(String genre);
}
