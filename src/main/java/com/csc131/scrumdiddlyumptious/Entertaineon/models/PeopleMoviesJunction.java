package com.csc131.scrumdiddlyumptious.Entertaineon.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "people_movies")
public class PeopleMoviesJunction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int people_movies_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	private Person person;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	
	private Movie movie;
	
	public PeopleMoviesJunction(Person person, Movie movie) {
		this.person = person;
		this.movie = movie;
	}

	public PeopleMoviesJunction() {
		
	}
	
	public int getPeople_movies_id() {
		return people_movies_id;
	}

	public void setPeople_movies_id(int people_movies_id) {
		this.people_movies_id = people_movies_id;
	}

	@JsonManagedReference
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@JsonManagedReference
	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@Override
	public String toString() {
		return "People_Movies [people_movies_id=" + people_movies_id + ", people=" + person + ", movies=" + movie
				+ "]";
	}
	
	
	
}
