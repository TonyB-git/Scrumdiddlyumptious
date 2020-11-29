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

public class RatingsFix {
	
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
	
	public static String urlIfyTitle(String title) {
		StringBuilder titleStringBuffer = new StringBuilder();
		if(!title.isEmpty()) {
			for(int j = 0; j < title.length(); j++) {
			
				switch(title.charAt(j)) {
					case ' ':
						titleStringBuffer.append('+');
						break;
						
					case '#':
						titleStringBuffer.append("%23");
						break;
						
					case '+':
						titleStringBuffer.append("%2B");
						break;
						
					case ',':
						titleStringBuffer.append("%2C");
						break;
						
					case '?':
						titleStringBuffer.append("%3F");
						break;
						
					case 'è':
						titleStringBuffer.append("%C3%A8");
						break;
					
					case 'ö':
						titleStringBuffer.append("%C3%B6");
						break;
						
					case 'ø':
						titleStringBuffer.append("%C3%B8");
						break;
						
					case 'í':
						titleStringBuffer.append("%C3%AD");
						break;
						
					case 'Á':
						titleStringBuffer.append("%C3%81");
						break;
						
					case '½':
						titleStringBuffer.append("%C2%BD");
						break;
						
					case 'â':
						titleStringBuffer.append("%C3%A2");
						break;
						
					case 'î':
						titleStringBuffer.append("%C3%AE");
						break;
						
					case 'à':
						titleStringBuffer.append("%C3%A0");
						break;
						
					case 'ä':
						titleStringBuffer.append("%C3%A4");
						break;
					
					case 'å':
						titleStringBuffer.append("%C3%A5");
						break;
						
					case 'ñ':
						titleStringBuffer.append("%C3%B1");
						break;
						
					case 'ó':
						titleStringBuffer.append("%C3%B3");
						break;
						
					case 'ô':
						titleStringBuffer.append("%C3%B4");
						break;
					
					case '…':
						titleStringBuffer.append("%E2%80%A6");
						break;
						
					default:
						titleStringBuffer.append(title.charAt(j));
						break;
				}
			}
			
			
		}
		
		return titleStringBuffer.toString();
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
		
		String address2 = "http://www.omdbapi.com/?apikey=889c29b4&t="; //used
		String address5 = "http://www.omdbapi.com/?apikey=b5f1e1ce&t="; //half used
		String address4 = "http://www.omdbapi.com/?apikey=7522967f&t="; //used
		String address1 = "http://www.omdbapi.com/?apikey=23cb7c39&t="; //used
		String address3 = "http://www.omdbapi.com/?apikey=a0784ce7&t="; //used
		String address6 = "http://www.omdbapi.com/?apikey=73637ace&t="; //used
		String address7 = "http://www.omdbapi.com/?apikey=ed49a6e8&t="; //used
		String address8 = "http://www.omdbapi.com/?apikey=69e5f359&t="; //used
		String address9 = "http://www.omdbapi.com/?apikey=f1a66fd&t=";
		
		
		
	
		
		HashMap<String, String> customQueries = new HashMap<String, String>();
		customQueries.put("The Battle Over Citizen Kane", "http://www.omdbapi.com/?apikey=73637ace&t=American+Experience&Season=8&Episode=7");
		customQueries.put("D-Day Remembered", "http://www.omdbapi.com/?apikey=73637ace&t=American+Experience&Season=6&Episode=7");
		customQueries.put("Radio Bikini", "http://www.omdbapi.com/?apikey=73637ace&t=American+Experience&Season=1&Episode=2");
		customQueries.put("The Ten-Year Lunch: The Wit and Legend of the Algonquin Round Table", "http://www.omdbapi.com/?apikey=73637ace&t=American+Masters&Season=2&Episode=9");
		customQueries.put("Waldo Salt: A Screenwriter's Journey", "http://www.omdbapi.com/?apikey=73637ace&t=American+Masters&y=1985&Season=6&Episode=9");
		customQueries.put("Isaac in America: A Journey with Isaac Bashevis Singer", "http://www.omdbapi.com/?apikey=73637ace&t=American+Masters&y=1985&Season=2&Episode=1");
		customQueries.put("Just Another Missing Kid", "http://www.omdbapi.com/?apikey=73637ace&i=tt0084185");
		customQueries.put("Bridge to Freedom: 1965", "http://www.omdbapi.com/?apikey=73637ace&i=tt0885811");
		
		
		Session session = sf.openSession();
		session.beginTransaction();
		StringBuilder queryBuilder = new StringBuilder();
		for(int i = 4836; i < 4841; i++) {
			Movie movie = session.find(Movie.class, i);
			if(movie == null) {
				continue;
			}
			
			
			String address = null;
			System.out.println(customQueries.get(movie.getTitle()));
			System.out.println(movie.getTitle());
			if(customQueries.containsKey(movie.getTitle())) {
				queryBuilder.append(customQueries.get(movie.getTitle()));
				
			}else {
				queryBuilder.append("http://www.omdbapi.com/?apikey=f1a66fd&t=");
				queryBuilder.append(urlIfyTitle(movie.getTitle()));
				queryBuilder.append("&y=");
				queryBuilder.append(String.valueOf(movie.getYear()));
			}
			
			address = queryBuilder.toString();
			
			//request
			JSONObject jsonResponse = getResource(address);
			if(jsonResponse.getString("Response").equals("False")){
				System.out.println(jsonResponse.getString("Error") + "\nItem number: " + i + " address: " + address);
				session.flush();
				session.clear();
				session.getTransaction().commit();
				session.close();
				
				System.exit(20);
			}
			
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
			
			Ratings ratings = new Ratings(imdb_rating, rotten_tomatoes_rating, metacritic_rating); 
			int id = (int) session.save(ratings);
			System.out.println("Id generated: " + id);
			
			movie.setRatings(ratings);
			ratings.setMovie(movie);
			
			if(i % 10 == 0) {
				session.flush();
				session.clear();
			}
			
			queryBuilder.setLength(0);
		
		}
		
		session.getTransaction().commit();
		session.close();
		
		
	}

}
