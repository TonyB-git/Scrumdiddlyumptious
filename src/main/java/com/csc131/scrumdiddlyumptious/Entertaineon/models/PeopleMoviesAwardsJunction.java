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
@Table(name = "people_awards")
public class PeopleMoviesAwardsJunction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int people_awards_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	private Person person;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "award_id")
	private Awards awards;
	
	public PeopleMoviesAwardsJunction(Movie movie, Person person, Awards awards) {
		this.movie = movie;
		this.person = person;
		this.awards = awards;
	}
	
	public PeopleMoviesAwardsJunction() {
		
	}

	public int getPeople_awards_id() {
		return people_awards_id;
	}

	public void setPeople_awards_id(int people_awards_id) {
		this.people_awards_id = people_awards_id;
	}

	@JsonManagedReference
	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@JsonManagedReference
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@JsonManagedReference
	public Awards getAwards() {
		return awards;
	}

	public void setAwards(Awards awards) {
		this.awards = awards;
	}

	@Override
	public String toString() {
		return "People_Awards [people_awards_id=" + people_awards_id + ", movies=" + movie + ", people=" + person
				+ ", awards=" + awards + "]";
	}
	
	
	
	
}
