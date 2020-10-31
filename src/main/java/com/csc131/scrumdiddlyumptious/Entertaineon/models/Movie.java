package com.csc131.scrumdiddlyumptious.Entertaineon.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="films")
public class Movie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int film_id;
	@Column(length=1000)
	private String title;
	private int year;
	
	public Movie() {
		
	}
	

	public int getFilm_id() {
		return film_id;
	}



	public void setFilm_id(int film_id) {
		this.film_id = film_id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public int getYear() {
		return year;
	}



	public void setYear(int year) {
		this.year = year;
	}


	@Override
	public String toString() {
		return "Movie [film_id=" + film_id + ", title=" + title + ", year=" + year + "]";
	}

	
}
