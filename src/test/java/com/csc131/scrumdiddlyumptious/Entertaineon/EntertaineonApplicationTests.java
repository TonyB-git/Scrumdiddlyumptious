package com.csc131.scrumdiddlyumptious.Entertaineon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.csc131.scrumdiddlyumptious.Entertaineon.models.Awards;
import com.csc131.scrumdiddlyumptious.Entertaineon.models.Movie;
import com.csc131.scrumdiddlyumptious.Entertaineon.models.PeopleMoviesAwardsJunction;
import com.csc131.scrumdiddlyumptious.Entertaineon.models.Person;
import com.csc131.scrumdiddlyumptious.Entertaineon.models.Ratings;

@SpringBootTest
class EntertaineonApplicationTests {

	///////////////////////////////////////////Awards Test///////////////////////////////////////////////////
	@Test
	void AwardConstructorTest() {
		Awards award = new Awards("testAward");
		
		assertEquals("Awards()", award.getType(), "testAward");
	}
	
	@Test
	void AwardTypeSetterTest() {
		Awards award = new Awards("testAward");
		
		award.setType("otherAward");
		assertEquals("setType()", award.getType(), "otherAward");
	}
	
	@Test
	void AwardIDTest() {
		Awards award = new Awards("testAward");
		award.setAward_id(10);
		assertEquals("getAward_id()", award.getAward_id(), 10);
	}
	
	@Test
	void addJunctionTest() {
		Awards award = new Awards("testAward");
		Ratings ratings = new Ratings("test", "test", "test");
		Movie movie = new Movie(ratings, "testTitle", 2020, "testRating", "testGenre", "testPlot", "testPoster");
		Person person = new Person("testName");
		award.addPeopleMoviesAwardsJunctionList(new PeopleMoviesAwardsJunction(movie, person, award));
		assertEquals("addPeopleMoviesAwardsJunctionList()", award.getPeopleMoviesAwardsJunctionList().size(), 1);
	}
	
	@Test
	void setJunctionSetTest() {
		Awards award = new Awards();
		Set<PeopleMoviesAwardsJunction> newSet = new HashSet<>();
		award.setPeopleMoviesAwardsJunctionList(newSet);
		assertNotEquals("setPeopleMoviesAwardsJunctionList()", award.getPeopleMoviesAwardsJunctionList(), null);
	}
	
	@Test
	void toStringTest() {
		Awards award = new Awards();
		String testString = "Awards [award_id=" + award.getAward_id() + ", type=" + award.getType() + ", people_awards_list=" + award.getPeopleMoviesAwardsJunctionList() + "]";
		
		assertEquals("toString()", testString, award.toString());
	}
	
	///////////////////////////////////////////Ratings Test///////////////////////////////////////////////////
	
	@Test
	void RatingsConstructorTest() {
		Ratings ratings = new Ratings("test1", "test2", "test3");
		
		assertEquals("Ratings()", ratings.getImdb_rating(), "test1");
		assertEquals("Ratings()", ratings.getRotten_tomatoes_rating(), "test2");
		assertEquals("Ratings()", ratings.getMetacritic_rating(), "test3");
	}
	
	@Test
	void imdbSetterTest() {
		Ratings ratings = new Ratings("test1", "test2", "test3");
		
		ratings.setImdb_rating("imdb");
		assertEquals("getImdb_rating()", ratings.getImdb_rating(), "imdb");
	
	}
	
	@Test
	void metacriticSetterTest() {
		Ratings ratings = new Ratings("test1", "test2", "test3");
		
		ratings.setMetacritic_rating("metacritic");
		assertEquals("getMetacritic_rating()", ratings.getMetacritic_rating(), "metacritic");
	
	}
	
	@Test
	void rottenTomatoeSetterTest() {
		Ratings ratings = new Ratings("test1", "test2", "test3");
		
		ratings.setRotten_tomatoes_rating("rotten");
		assertEquals("getRotten_tomatoes_rating()", ratings.getRotten_tomatoes_rating(), "rotten");
	
	}
}
