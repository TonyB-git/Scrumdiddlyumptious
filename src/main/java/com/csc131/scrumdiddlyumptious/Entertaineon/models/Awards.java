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
@Table(name = "awards")
public class Awards {
	
	/*
	 * Private Members
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int award_id;
	
	@Column(length = 1000)
	private String type;
	
	@OneToMany(mappedBy = "awards", cascade=CascadeType.ALL)
	private Set<PeopleMoviesAwardsJunction> peopleMoviesAwardsJunctionList = new HashSet<PeopleMoviesAwardsJunction>();
	
	/*
	 * Constructors
	 * 
	 */
	
	public Awards(String type) {
		this.type = type;
	}
	
	public Awards() {
		
	}
	
	/*
	 * Getters
	 * 
	 */

	public int getAward_id() {
		return award_id;
	}
	
	public String getType() {
		return type;
	}
	
	@JsonBackReference
	public Set<PeopleMoviesAwardsJunction> getPeopleMoviesAwardsJunctionList() {
		return peopleMoviesAwardsJunctionList;
	}
	
	/*
	 * Setters
	 * 
	 */

	public void setAward_id(int award_id) {
		this.award_id = award_id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPeopleMoviesAwardsJunctionList(Set<PeopleMoviesAwardsJunction> peopleMoviesAwardsJunctionList) {
		this.peopleMoviesAwardsJunctionList = peopleMoviesAwardsJunctionList;
	}
	
	/*
	 * Adders
	 * 
	 */
	
	public void addPeopleMoviesAwardsJunctionList(PeopleMoviesAwardsJunction people_awards) {
		this.peopleMoviesAwardsJunctionList.add(people_awards);
		people_awards.setAwards(this);
	}
	
	/*
	 * Miscellaneous
	 * 
	 */

	@Override
	public String toString() {
		return "Awards [award_id=" + award_id + ", type=" + type + ", people_awards_list=" + peopleMoviesAwardsJunctionList + "]";
	}
	
	
}
