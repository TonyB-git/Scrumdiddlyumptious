package com.csc131.scrumdiddlyumptious.Entertaineon.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ratings")
public class Ratings {
	
	/*
	 * Private Members
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rating_id;
	
	private String imdb_rating;
	
	private String rotten_tomatoes_rating;
	
	private String metacritic_rating;
	
	@OneToOne(mappedBy = "movieRatings")
	private Movie movie;
	
	/*
	 * Constructors
	 * 
	 */
	
	public Ratings(String imdb_rating, String rotten_tomatoes_rating, String metacritic_rating) {
		this.imdb_rating = imdb_rating;
		this.rotten_tomatoes_rating = rotten_tomatoes_rating;
		this.metacritic_rating = metacritic_rating;
	}
	
	public Ratings() {
		
	}
	
	/*
	 * Getters
	 * 
	 */

	public int getRating_id() {
		return rating_id;
	}
	
	public String getImdb_rating() {
		return imdb_rating;
	}
	
	public String getRotten_tomatoes_rating() {
		return rotten_tomatoes_rating;
	}
	
	@JsonBackReference
	public Movie getMovie() {
		return movie;
	}
	
	public String getMetacritic_rating() {
		return metacritic_rating;
	}
	
	/*
	 * Setters
	 * 
	 */

	public void setRating_id(int rating_id) {
		this.rating_id = rating_id;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public void setImdb_rating(String imdb_rating) {
		this.imdb_rating = imdb_rating;
	}

	public void setRotten_tomatoes_rating(String rotten_tomatoes_rating) {
		this.rotten_tomatoes_rating = rotten_tomatoes_rating;
	}

	public void setMetacritic_rating(String metacritic_rating) {
		this.metacritic_rating = metacritic_rating;
	}
	
	/*
	 * Miscellaneous
	 * 
	 */

	@Override
	public String toString() {
		return "Ratings [rating_id=" + rating_id + ", imdb_rating=" + imdb_rating + ", rotten_tomatoes_rating="
				+ rotten_tomatoes_rating + ", metacritic_rating=" + metacritic_rating + "]";
	}
	
}
