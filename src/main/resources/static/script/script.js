// JavaScript Document

var filter = document.getElementById("filterBy");
var content = document.getElementById("content");
var btn = document.getElementById("btn");
var searchBar = document.getElementById("search");
var newBtn = document.getElementById("selectMovie");
var availableMovies = []; // Array for Movie Search
var availableActors = []; // Array for Actor Search
var availableYears = []; // Array for Year Search
var availableAwards = []; // Array for Award Search
var availableGenre = []; // Array for Genre Search
var i;

// Populate Search Arrays
$(function(){
	//Fill Actor Array
	$.ajax({
		method : 'get',
		url : '/rest/actors/all',
		success: function(data){
			for(i=0; i < data.length; i++){
				availableActors.push(data[i].name)
			}
			gotActor = true;
		}
	})
	//Fill Award Array
	$.ajax({
		method : 'get',
		url : '/rest/awards/all',
		success: function(data){
			for(i=0; i < data.length; i++){
				availableAwards.push(data[i].type)
			}
		}
	})
	//Fill Movie, Year, and Genre Arrays
	$.ajax({
		method : 'get',
		url : '/rest/movies/all',
		success: function(data){
			for(i = 0; i < data.length; i++){
				availableMovies.push(data[i].title);
				var qoutes = '';
				//Make Year a String
				var dummyYear = qoutes.concat(data[i].year);
				//Check if Year is already in Year Array, if not add year
				if(!availableYears.includes(dummyYear)){
					var qoutes = '';
						availableYears.push(dummyYear);
				}
				//Check if Genre is already in Genre Array, if not add Genre
				if(!availableGenre.includes(data[i].genre)){
					availableGenre.push(data[i].genre);
				}
				}
			//Hide loading bar after arrays get filled
			document.getElementById("loader").style.display = "none";
		}
	})
});
//Change filter options on change
filter.addEventListener("change", function(){
	//get value of filter option
	var filter = document.getElementById("filterBy").value;	
	//show standard search and hide advanced search
	document.getElementById("standard").style.display = "block";
	document.getElementById("advanced").style.display = "none";
	//select which filter to search by
	switch(filter){
		case "year":
			$( "#search" ).autocomplete({
				source: availableYears
			});
			break;
		case "actor":
			$( "#search" ).autocomplete({
				source: availableActors
			});
			break;
		case "advanced":
		//hide standard search and show advanced search
			document.getElementById("standard").style.display = "none";
			document.getElementById("advanced").style.display = "block";
			$( "#advanceSearchYear" ).autocomplete({
				source: availableYears
			});
			$( "#advanceSearchAward" ).autocomplete({
				source: availableAwards
			});
			break;
		case "award":
			$( "#search" ).autocomplete({
				source: availableAwards
			});
			break;
		case "genre":
			$( "#search" ).autocomplete({
				source: availableGenre
			});
			break;			
		case "film":
			$( "#search" ).autocomplete({
				source: availableMovies
			});
			break;
	}
});

//pull movie information on button press
btn.addEventListener("click", function() {
	//clear page contents
	document.getElementById("content").innerHTML = "";
	var searchBy = document.getElementById("search").value;
	var searchByYear = document.getElementById("advanceSearchYear").value;
	var searchByAward = document.getElementById("advanceSearchAward").value;
	var filter = document.getElementById("filterBy").value;
	var data, externalData;
	//call get method based on search filter selected
	switch(filter){
		case "year":
			$.ajax({
				method : 'get',
				url : '/rest/movies/year/' + searchBy,
				success: function(data){
					for(var i = 0; i < data.length; i++){
						var dataYear = data[i];
						renderHTMLYear(dataYear);
					}
				}
			})
			break;
		case "actor":
			$.ajax({
				method : 'get',
				url : '/rest/movies/actor/' + searchBy,
				success: function(data){
					for(var i = 0; i < data.length; i++){
						var dataActor = data[i];
						renderHTMLYear(dataActor);
					}
				}
			})
			break;
		case "award":
			$.ajax({
				method : 'get',
				url : '/rest/movies/award/' + searchBy,
				success: function(data){
					for(var i = 0; i < data.length; i++){
						var dataAward = data[i];
						renderHTMLYear(dataAward);
					}
				}
			})
			break;
		case "advanced":
			$.ajax({
				method : 'get',
				url : '/rest/search?year=' + searchByYear + '&award=' + searchByAward,
				success: function(data){
					console.log("out Here");
					for(var i = 0; i < data.length; i++){
						console.log("In Here");
						var dataAward = data[i];
						renderHTMLYear(dataAward);
					}
				}
			})
			break;
		case "genre":
			$.ajax({
				method : 'get',
				url : '/rest/movies/genre/' + searchBy,
				success: function(data){
					for(var i = 0; i < data.length; i++){
						var dataAward = data[i];
						renderHTMLYear(dataAward);
					}
				}
			})
			break;
		case "film":
			$.ajax({
				method : 'get',
				url : '/rest/movies/title/' + searchBy,
				success: function(data){
					$.ajax({
						method : 'get',
						url : 'http://www.omdbapi.com/?apikey=a0784ce7&t=' + searchBy,
						success: function(externalData){
							renderHTMLMovie(data, externalData);
						}
					})
				}
			})
			break;
	}
});	
	
/*
newBtn.addEventListener("onclick", function() {
	document.getElementById("content").innerHTML = "";
	var searchBy = document.getElementById("selectMovie").value;
	var data, externalData;
	$.ajax({
		method : 'get',
		url : '/rest/movies/title/' + searchBy,
		success: function(data){
			$.ajax({
				method : 'get',
				url : 'http://www.omdbapi.com/?apikey=a0784ce7&t=' + searchBy,
				success: function(externalData){
					renderHTMLMovie(data, externalData);
				}
			})
		}
	})
})
*/
//get movie information for selected title
function selectMovie(searchBy){
	$.ajax({
				method : 'get',
				url : '/rest/movies/title/' + searchBy,
				success: function(data){
					$.ajax({
						method : 'get',
						url : 'http://www.omdbapi.com/?apikey=a0784ce7&t=' + searchBy,
						success: function(externalData){
							renderHTMLMovie(data, externalData);
						}
					})
				}
			})
}

//Display all movies that meet search criteria
function renderHTMLYear(data){
	var movieContent = "";
	if(data.poster == "N/A"){
		$.ajax({
				method : 'get',
				url : 'http://www.omdbapi.com/?apikey=a0784ce7&t=' + data.title,
				success: function(externalData){
					if(externalData =="N/A"){
						movieContent = "<span style='float: left; width: 215px; height: 300px;'><button style='cursor: pointer;' id='selectMovie' value='" + externalData.Title + "' onclick='selectMovie(value)'><table><tr><td style='width: 180px'>" + externalData.Title + "</td></tr></table></button></span>";
						content.insertAdjacentHTML('afterend', movieContent);
					}else{
						movieContent = "<span style='float: left; width: 215px; height: 300px;'><button style='cursor: pointer;' id='selectMovie' value='" + data.title + "' onclick='selectMovie(value)'><table><tr><td><img style='width: 165px;' id='poster' alt=" + externalData.Title + " src=" + externalData.Poster + "/></td></tr><tr><td style='width: 180px'>" + externalData.Title + "</td></tr></table></button></span>";
						content.insertAdjacentHTML('beforeend', movieContent);
					}
				}
			})

	}else{
		movieContent = "<span style='float: left; width: 215px; height: 300px;'><button style='cursor: pointer;' id='selectMovie' value='" + data.title + "' onclick='selectMovie(value)'><table><tr><td><img style='width: 165px;' id='poster' alt=" + data.title + " src=" + data.poster + "/></td></tr><tr><td style='width: 180px'>" + data.title + "</td></tr></table></button></span>";
		content.insertAdjacentHTML('beforeend', movieContent);
	}
};

//Display detailed information about movie
function renderHTMLMovie(data, externalData){
	document.getElementById("content").innerHTML = "";
	var movieContent = "";
	if(externalData.Response == "True"){
		if(data.poster == "N/A"){
			movieContent = "<div><img id='poster' alt=" + data.title +" src=" + externalData.Poster + "/><br /><br /><h3> Movie: " + data.title + "</h3><h4> Rated: " + data.rated + "</h4><h4> Release Year: " + data.year + "</h4><h4> Actors: " + externalData.Actors + "</h4><p> Plot:</p><p>" + data.plot + "</p><p>Awards:</p><p>" + externalData.Awards + "</p><table><tr><th>Internet Movie Database</th><th>Rotten Tomatoes</th><th>Metacritic</th><th>Metascore</th><th>imdbRating</th><th>imdbVotes</th></tr><tr><td>" + data.ratings.imdb_rating + "</td><td>" + data.ratings.rotten_tomatoes_rating + "</td><td>" + data.ratings.metacritic_rating + "</td><td>" + externalData.Metascore + "</td><td>" + externalData.imdbRating + "</td><td>" + externalData.imdbVotes + "</td></tr></table><br /><a href='https://www.imdb.com/title/" + externalData.imdbID + "'><img id='imdbLink' src='./images/IMDB.png' /></a>";
		}else{
			movieContent = "<div><img id='poster' alt=" + data.title +" src=" + data.poster + "/><br /><br /><h3> Movie: " + data.title + "</h3><h4> Rated: " + data.rated + "</h4><h4> Release Year: " + data.year + "</h4><h4> Actors: " + externalData.Actors + "</h4><p> Plot:</p><p>" + data.plot + "</p><p>Awards:</p><p>" + externalData.Awards + "</p><table><tr><th>Internet Movie Database</th><th>Rotten Tomatoes</th><th>Metacritic</th><th>Metascore</th><th>imdbRating</th><th>imdbVotes</th></tr><tr><td>" + data.ratings.imdb_rating + "</td><td>" + data.ratings.rotten_tomatoes_rating + "</td><td>" + data.ratings.metacritic_rating + "</td><td>" + externalData.Metascore + "</td><td>" + externalData.imdbRating + "</td><td>" + externalData.imdbVotes + "</td></tr></table><br /><a href='https://www.imdb.com/title/" + externalData.imdbID + "'><img id='imdbLink' src='./images/IMDB.png' /></a>";
		}
	}else{
		movieContent = "<h4>" + externalData.Error + "</h4>"
	}

	content.insertAdjacentHTML('beforeend', movieContent);
};

function howToGuide(){
	document.getElementById("content").innerHTML = "";
	var checkBox = document.getElementById("howTo");
	if(checkBox.checked == true){
		document.getElementById("mySearch").style.display = "none";
		document.getElementById("howTo").style.display = "block";
		document.getElementById("content").innerHTML = "<div><p>use this to search by year and Award " + window.location.href + "search?year=<strong>{year}</strong>&award=<strong>{award}</strong></p><br /><p>use this to search all movies " + window.location.href + "rest/movies/all</P><p>use this to search all actors " + window.location.href + "rest/actors/all</P><br /><p>use this to search all awards " + window.location.href + "rest/awards/all</P><br /><br /><p>use this to search movies by title " + window.location.href + "rest/movies/title/<strong>{title}</strong></P><br /><p>use this to search movies by year " + window.location.href + "rest/movies/year/<strong>{year}</strong></P><br /><p>use this to search movies by actor " + window.location.href + "rest/movies/actor/<strong>{actor}</strong></P><br /><p>use this to search movies by awards " + window.location.href + "rest/movies/award/<strong>{award}</strong></P><br /><p>use this to search movies by genre" + window.location.href + "rest/movies/genre/<strong>{genre}</strong></P><br /></div>";
	}else{
		document.getElementById("howTo").style.display = "none";
		document.getElementById("mySearch").style.display = "block";
	}
};