// JavaScript Document
var content = document.getElementById("content");
var btn = document.getElementById("btn");
btn.addEventListener("click", function() {
	document.getElementById("content").innerHTML = "";
	var ourRequest = new XMLHttpRequest();
	var movie = document.getElementById("search").value;
	var filter = document.getElementById("filterBy").value;
	console.log(filter);
	console.log(movie);
	ourRequest.open('GET', 'http://www.omdbapi.com/?apikey=a0784ce7&t=' + movie);
	ourRequest.onload = function(){
		var ourData = JSON.parse(ourRequest.responseText);
		renderHTML(ourData);
	};
	ourRequest.send();
});


function renderHTML(data) {

	movieContent = "<img id='poster'" + data.Title +" src=" + data.Poster + "/><br /><br /><h3> Movie: " + data.Title + "</h3><h4> Rated: " + data.Rated + "</h4><h4> Release Year: " + data.Year + "</h4><h4> Actors: " + data.Actors + "</h4><p> Plot:< br />" + data.Plot + "</p><br /><br /><br /><table><tr><th>" + data.Ratings[0].Source + "</th><th>" + data.Ratings[1].Source + "</th><th>" + data.Ratings[2].Source + "</th><th>Metascore</th><th>imdbRating</th><th>imdbVotes</th></tr><tr><td>" + data.Ratings[0].Value + "</td><td>" + data.Ratings[1].Value + "</td><td>" + data.Ratings[2].Value + "</td><td>" + data.Metascore + "</td><td>" + data.imdbRating + "</td><td>" + data.imdbVotes + "</td></tr></table><br /><a href='https://www.imdb.com/title/" + data.imdbID + "'><img id='imdbLink' src='../images/IMDB.png' /></a>";
	content.insertAdjacentHTML('beforeend', movieContent);
};

