package com.csc131.scrumdiddlyumptious.Entertaineon.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "movies")
public class Movie {
	
	/*
	 * Private Members
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int movie_id;
	
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "rating_id")
	private Ratings movieRatings;
	
	
	@OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<PeopleMoviesAwardsJunction> peopleMoviesAwardsJunctionList = new HashSet<PeopleMoviesAwardsJunction>();
	
	
	@OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<PeopleMoviesJunction> peopleMoviesJunctionList = new HashSet<PeopleMoviesJunction>();
	
	@Column(length = 1000)
	private String title;
	
	private int year;
	
	@Column(length = 10)
	private String rated;
	
	@Column(length = 100)
	private String genre;
	
	@Column(length = 10000)
	private String plot;
	
	@Column(length = 1000)
	private String poster;
	
	/*
	 * Constructors
	 * 
	 */
	
	public Movie(Ratings ratings, String title, int year, String rated, String genre, String plot, String poster) {
		this.movieRatings = ratings;
		this.title = title;
		this.year = year;
		this.rated = rated;
		this.genre = genre;
		this.plot = plot;
		this.poster = poster;
	}
	
	public Movie() {
		
	}
	
	/*
	 * Getters
	 * 
	 */
	
	public int getMovie_id() {
		return movie_id;
	}
	
	@JsonManagedReference
	public Ratings getRatings() {
		return movieRatings;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getYear() {
		return year;
	}

	public String getRated() {
		return rated;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public String getPlot() {
		return plot;
	}
	
	public String getPoster() {
		return poster;
	}
	
	@JsonBackReference
	public Set<PeopleMoviesAwardsJunction> getPeopleMoviesAwardsJunctionList() {
		return peopleMoviesAwardsJunctionList;
	}
	
	@JsonBackReference
	public Set<PeopleMoviesJunction> getPeopleMoviesJunctionList() {
		return peopleMoviesJunctionList;
	}
	
	/*
	 * Setters
	 * 
	 */	
	
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}

	public void setRatings(Ratings ratings) {
		this.movieRatings = ratings;
		//ratings.setMovie(this);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public void setRated(String rated) {
		this.rated = rated;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}
	
	public void setPoster(String poster) {
		this.poster = poster;
	}

	public void setPeopleMoviesAwardsJunctionList(Set<PeopleMoviesAwardsJunction> peopleMoviesAwardsJunctionList) {
		this.peopleMoviesAwardsJunctionList = peopleMoviesAwardsJunctionList;
	}

	public void setPeopleMoviesJunctionList(Set<PeopleMoviesJunction> peopleMoviesJunctionList) {
		this.peopleMoviesJunctionList = peopleMoviesJunctionList;
	}
	
	/*
	 * Adders
	 * 
	 */
	
	public void addPeopleMoviesAwardsJunctionList(PeopleMoviesAwardsJunction people_awards) {
		this.peopleMoviesAwardsJunctionList.add(people_awards);
		people_awards.setMovie(this);
	}

	
	public void addPeopleMoviesJunctionList(PeopleMoviesJunction people_movies) {
		this.peopleMoviesJunctionList.add(people_movies);
		people_movies.setMovie(this);
	}
	
	/*
	 * Miscellaneous
	 * 
	 */
	
	@Override
	public String toString() {
		return "Movies [movie_id=" + movie_id + ", ratings=" + movieRatings + ", peopleAwardsList=" + peopleMoviesAwardsJunctionList
				+ ", peopleMoviesList=" + peopleMoviesJunctionList + ", title=" + title + ", year=" + year + ", rated=" + rated
				+ ", genre=" + genre + ", plot=" + plot + ", poster=" + poster + "]";
	}

	
	
}
