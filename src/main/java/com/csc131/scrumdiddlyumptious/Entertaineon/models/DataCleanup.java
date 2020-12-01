package com.csc131.scrumdiddlyumptious.Entertaineon.models;

import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.json.*;



import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;


import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class DataCleanup {
	
	public static JSONObject getResource(String address) {
		JSONObject jsonObj = null;
		System.out.println("getting resource");
		try{
			jsonObj = Unirest.get(address).asJson().getBody().getObject();
			if(jsonObj == null) {
				System.out.println("was null");
			}
		} catch(UnirestException e) {
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("here");
			e.printStackTrace();
		}
		
		return jsonObj;
	}
	
	public static void main(String [] args) {
		Configuration config = new Configuration();
		config.configure().addAnnotatedClass(Person.class);
		config.configure().addAnnotatedClass(Movie.class);
		config.configure().addAnnotatedClass(PeopleMoviesJunction.class);
		config.configure().addAnnotatedClass(Awards.class);
		config.configure().addAnnotatedClass(PeopleMoviesAwardsJunction.class);
		config.configure().addAnnotatedClass(Ratings.class);
		
		SessionFactory sf = config.buildSessionFactory();
		
		
		
		String address1 = "http://www.omdbapi.com/?apikey=73637ace&t=American+Masters&Season=2&Episode=9";
		String address2 = "http://www.omdbapi.com/?apikey=73637ace&i=tt0084185";
		String address3 = "http://www.omdbapi.com/?apikey=73637ace&t=American+Masters&y=1985&Season=6&Episode=9";
		String address4 = "http://www.omdbapi.com/?apikey=73637ace&t=American+Masters&y=1985&Season=2&Episode=1";
		String address5 = "http://www.omdbapi.com/?apikey=73637ace&i=tt0885811";
		String address6 = "http://www.omdbapi.com/?apikey=73637ace&t=American+Experience&Season=1&Episode=2";
		String address7 = "http://www.omdbapi.com/?apikey=73637ace&t=American+Experience&Season=6&Episode=7";
		String address8 = "http://www.omdbapi.com/?apikey=73637ace&t=American+Experience&Season=8&Episode=7";
		
		ArrayList<String> list = new ArrayList<String>();
		list.add(address1);
		list.add(address8);
		list.add(address7);
		list.add(address6);
		list.add(address5);
		list.add(address4);
		list.add(address3);
		list.add(address2);
		
		
		Session session = sf.openSession();
		session.beginTransaction();
		for(String address : list) {
			JSONObject jsonResponse = getResource(address);
			
			
			String title = jsonResponse.getString("Title");
			String genre = jsonResponse.getString("Genre");
			String plot = jsonResponse.getString("Plot");
			String poster = jsonResponse.getString("Poster");
			String rated = jsonResponse.getString("Rated");
			String runtime = jsonResponse.getString("Runtime");
			
			
			//this bit just breaks out all the individual ratings from the array;
			JSONArray ratingsArray = jsonResponse.getJSONArray("Ratings");
			String metacritic_rating = "N/A";
			String rotten_tomatoes_rating = "N/A";
			String imdb_rating = "N/A";
			for(int j = 0; j < ratingsArray.length(); j++) {
				JSONObject arrayObject = ((JSONObject)ratingsArray.get(j));
				String source = arrayObject.getString("Source");
				String value = arrayObject.getString("Value");
				if(source.equals("Internet Movie Database")) {
					imdb_rating = value;
				}else if(source.equals("Rotten Tomatoes")) {
					rotten_tomatoes_rating = value;
				}else {
					metacritic_rating = value;
				}
			}
			
			
			//create rating table entry and save it
			//save makes the Ratings entry a "managed" entry by hibernate so now we can use any setters and hibernate will change the entry appropriately before
			//pushing to the database
			Ratings ratings = new Ratings(imdb_rating, rotten_tomatoes_rating, metacritic_rating); 
			session.save(ratings);
			
			Query query = session.createQuery("Select m from Movie m where title = :title");
			query.setParameter("title", title);
			
			Movie movie = (Movie) query.getSingleResult();
			if(movie == null)
			{
				System.out.println("title: " + title + " came back null from db!");
				System.exit(10);
			}
			
			movie.setGenre(genre);
			movie.setPlot(plot);
			movie.setPoster(poster);
			movie.setRated(rated);
			movie.setRatings(ratings);
			
			
			ratings.setMovie(movie);
			
			
			
		}
		
		session.getTransaction().commit();
		session.close();
		
		
	}

}
