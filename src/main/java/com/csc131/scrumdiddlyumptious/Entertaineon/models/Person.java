package com.csc131.scrumdiddlyumptious.Entertaineon.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "people")
public class Person {
	
	/*
	 * Private Members
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int person_id;
	
	@Column(length = 1000)
	private String name;
	
	@OneToMany(mappedBy = "person", cascade=CascadeType.ALL)
	private Set<PeopleMoviesAwardsJunction> peopleMoviesAwardsJunctionList = new HashSet<PeopleMoviesAwardsJunction>();
	
	@OneToMany(mappedBy = "person", cascade=CascadeType.ALL)
	private Set<PeopleMoviesJunction> peopleMoviesJunctionList = new HashSet<PeopleMoviesJunction>();
	
	/*
	 * Constructors
	 * 
	 */
	
	public Person(String name) {
		this.name = name;
	}
	
	public Person() {
		
	}
	
	
	/*
	 * Getters
	 * 
	 */

	public int getPerson_id() {
		return person_id;
	}
	
	public String getName() {
		return name;
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
	
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPeopleMoviesAwardsJunctionList(Set<PeopleMoviesAwardsJunction> peopleMoviesAwardsJunctionList){
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
		people_awards.setPerson(this);
	}
	
	public void addPeopleMoviesJunctionList(PeopleMoviesJunction people_movies) {
		this.peopleMoviesJunctionList.add(people_movies);
		people_movies.setPerson(this);
	}
	
	/*
	 * Miscellaneous
	 * 
	 */

	@Override
	public String toString() {
		return "People [person_id=" + person_id + ", name=" + name + ", peopleAwardsList=" + peopleMoviesAwardsJunctionList
				+ ", peopleMoviesList=" + peopleMoviesJunctionList + "]";
	}
	
	
}
