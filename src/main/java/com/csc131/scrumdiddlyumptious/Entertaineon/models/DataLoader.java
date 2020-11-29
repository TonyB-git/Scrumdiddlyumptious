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

public class DataLoader {
	

	
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
	
	public static JSONObject getResource(String address, String title, String year) {
		JSONObject jsonObj = null;
		System.out.println("getting resource");
		try{
			jsonObj = Unirest.get(address + title + year).asJson().getBody().getObject();
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
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String address = "http://www.omdbapi.com/?apikey=23cb7c39&t=";
		CSVParser csvp = new CSVParser("C:\\Users\\reloueslati\\Desktop\\KaggleData_the_oscar_award.csv", true);
		
		ArrayList<String> list = csvp.parse();
		Configuration config = new Configuration();
		
		config.configure().addAnnotatedClass(Person.class);
		config.configure().addAnnotatedClass(Movie.class);
		config.configure().addAnnotatedClass(PeopleMoviesJunction.class);
		config.configure().addAnnotatedClass(Awards.class);
		config.configure().addAnnotatedClass(PeopleMoviesAwardsJunction.class);
		config.configure().addAnnotatedClass(Ratings.class);
		
		SessionFactory sf = config.buildSessionFactory();
		
		//helps us to get sessions with the database
		
		
		
		//this will store all movies we couldn't add and the reason why
		HashMap<String, String> unable = new HashMap<>();
		
		//these are the keys I have so far but we could probably do this a better way
		String address2 = "http://www.omdbapi.com/?apikey=889c29b4&t=";
		String address5 = "http://www.omdbapi.com/?apikey=b5f1e1ce&t=";
		String address4 = "http://www.omdbapi.com/?apikey=7522967f&t=";
		String address1 = "http://www.omdbapi.com/?apikey=23cb7c39&t=";
		String address3 = "http://www.omdbapi.com/?apikey=a0784ce7&t=";
		String address6 = "http://www.omdbapi.com/?apikey=73637ace&t=";
		String address7 = "http://www.omdbapi.com/?apikey=ed49a6e8&t=";
		
		
		
		//this is so i can rotate keys when we get a return of "limit exceeded" due to only being able to poll 1000 times a day per key
		List<String> addresses = new ArrayList<String>();
		addresses.add(address3);
		addresses.add(address1); 
		
		addresses.add(address6);
		addresses.add(address2);
		addresses.add(address4);
		addresses.add(address5);
		addresses.add(address7); //used today
		
		
		
		//this is necessary to create a database insert/update
		Session session = sf.openSession();
	
		//this is necessary to create a database insert/update
		session.beginTransaction();
		
		//this is for cycling keys
		int count = 0;
		
		
		String previous = "";                            //this is the previous title processed (for debugging purposes)
		String repeat = "";                              //this is to see titles that are mentioned multiple times (for debugging but not necessary anymore)
		HashSet<String> touched = new HashSet<>();       //for making sure we don't process the same title again
		boolean yearAhead = false;                       //to see if the year in kaggle data was 1 behind actual year in imdb
		boolean yearBehind = false;                      //to see if the year in kaggle data was 1 ahead of actual year in imdb
		
		//go through kaggle data 7 items at a time (each group of 7 pertains to one movie)
		//I could have written this to go through all the database items instead but I didn't want a million queries look through for my own debugging stuff
		
		//im using this loop to load from a previous "save" point because at movie 1500/4800 I had to make about 40 database adjustments
		int start = 0;
		for(int i = 0; i < list.size(); i+=7) {
			
			//this just starts me a the index for the save point
			if(list.get(i+5).equals("Crazy Heart")) {
				start = i;
				break;
			}
		}
		
		for(int i = start; i < list.size(); i+= 7) {
			
			//just using the year and title to form a rest request to imdb
			int year = Integer.parseInt(list.get(i));
			String title = list.get(i+5);

			/*if(title.equals("Crazy Heart")) {
				System.out.println(1960);
				session.flush();
			    session.clear();
				System.exit(50);
				
			}*/
			
			if(i % 7000 == 0) {
				count++;
			}
			
			
			
			//don't process empty titles
			if(!title.isEmpty()) {
				//for separating processes clearly in debugger
				System.out.println("\n\n\n");
				
				//if we have it in the set, we don't care to ask imdb again
				if(touched.contains(title + String.valueOf(year))) {
					repeat = title;
					System.out.println("Set contained " + title);
					continue;
					
				//else add it to the set (we use year to differentiate remakes)	
				}else {
					System.out.println(title + String.valueOf(year));
					touched.add(title + String.valueOf(year));
				}
				
				//this just spits out what we are actually sending imdb so we can debug later
				System.out.println("Title to be requested " + addresses.get(count) + urlIfyTitle(title) + "&y="+year);
				
				//send the request to imdb
				JSONObject jsonResponse = getResource(addresses.get(count), urlIfyTitle(title), "&y="+year);
				if(jsonResponse.getString("Response").equals("False")) {
					
					//try 1 year ahead if not successful
					jsonResponse = getResource(addresses.get(count), urlIfyTitle(title), "&y="+(year+1));
					if(jsonResponse.getString("Response").equals("False")) {
						
						//try 1 year behind if not successful
						jsonResponse = getResource(addresses.get(count), urlIfyTitle(title), "&y="+(year-1));
						if(jsonResponse.getString("Response").equals("False")) {
							
							//since trying different years consumes a request, we need to make sure our keys didnt go bad
							if(jsonResponse.getString("Response").equals("False") && jsonResponse.getString("Error").equals("Request limit reached!")) {
								count++;
								jsonResponse = getResource(addresses.get(count), urlIfyTitle(title), "&y="+year);
								
								//if all else fails, we just print the reason why (maybe imdb doesnt have it or something)
								if(jsonResponse.getString("Response").equals("False")) {
									unable.put(title, jsonResponse.getString("Error"));
									System.out.println("Couldnt request " + title);
									System.out.println("repeat item " + repeat);
									System.out.println(jsonResponse.toString());
									System.out.println(addresses.get(0) + urlIfyTitle(title) + "&y=" + year);
									System.exit(5);
								}
							}
							
						}else {
							//to update database 
							yearBehind = true;
						}
						
					}else {
						//to update database 
						yearAhead = true;
					}
					
				}
				
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
				
				
				//debug stuff
				System.out.println("After request: " + title);
				System.out.println(address + urlIfyTitle(title));
				System.out.println(jsonResponse.toString());
				
				
				//get the items we want from the json
				String genre = jsonResponse.getString("Genre");
				String plot = jsonResponse.getString("Plot");
				String poster = jsonResponse.getString("Poster");
				String rated = jsonResponse.getString("Rated");
				String runtime = jsonResponse.getString("Runtime");
				
				
				//multiple corresponds to asking the db for movies that have remakes
				boolean multiple = false;
				
				Movie movie = null;
				
				try {
					//this queries the db for the movie title (and puts that movie in "managed" state so we can use setters to actually push updates)
					System.out.println("Querying database for " + title);
					Query query = session.createQuery("Select m from Movie m where title = :title");
					query.setParameter("title", title);
					List<Movie> movies = (List<Movie>) query.getResultList();
					
					//if we found a bunch of remakes
					if(movies.size() > 1) {
						multiple = true;
						for(Movie movieToFind : movies){
							if(movieToFind.getYear() == year) {
								System.out.println("Matched year " + year);
								movie = movieToFind;
							}else {
								System.out.println(movieToFind.getYear() + " did not match year " + year);
							}
						}
						System.out.println("Got multiple results");
						
					//we found a single result 
					}else{
						movie = movies.get(0);
						System.out.println("Got single result");
					}
					
				}catch(EntityNotFoundException e1) {
					
					//the only way we get here is from really bad stuff happening in the query return
					System.out.println("Exception after query\nRepeat that was last set: " + repeat);
					System.out.println("Previous title processed: " + previous);
					System.out.println("Title attempted: " + addresses.get(count) + urlIfyTitle(title) + "&y="+year + " " + multiple);
					
					e1.printStackTrace();
					System.exit(5);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				
				//since we pull the movie out of the db through a query, it is now "managed"
				//we can use setters to actually update the db entry and itll be commited soon
				
				if(movie == null) {
					System.out.println("movie was null");
					System.exit(6);
				}
				
				movie.setGenre(genre);
				movie.setPlot(plot);
				movie.setPoster(poster);
				movie.setRated(rated);
				if(yearAhead) {
					movie.setYear(year + 1);
					yearAhead = false;
				}
				
				if(yearBehind) {
					movie.setYear(year - 1);
					yearBehind = false;
				}
				
				movie.setRatings(ratings);
				
				
				ratings.setMovie(movie);
				
					
					
				//update what movie was just processed (for debugging purposes)
				previous = addresses.get(count) + urlIfyTitle(title) + "&y="+year + " " + multiple;
				
				//hibernate loves to cache things and we don't want to have a bottleneck or problem while doing batch inserts/updates
				//so we just flush stuff every 50 items
				if( i % 50 == 0 ) { 
				      //flush a batch of inserts and release memory:
					try {
					      session.flush();
					      session.clear();
					}catch(EntityNotFoundException e) {
						
						e.printStackTrace();
						System.exit(5);
					}
				}
			}
			
		}
		
		//completes the batch transaction
		session.getTransaction().commit();
		session.close();
		
		//this just writes all the movies we couldn't process to a file (I don't expect this actually write since I hand most stuff in the code above).
		try {
			FileWriter outFile = new FileWriter("output.txt");
			for (Map.Entry<String,String> entry : unable.entrySet()) {  
	            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
	            
	            outFile.write(entry.getKey() + " " + entry.getValue() + "\n");
			
			}
			
			outFile.close();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			
		}
		
		
	}

}
