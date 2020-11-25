// JavaScript Document
$(document).ready(function(){
	//var apiKey = "a0784ce7";
	$("#mySearch").submit(function(event){
		event.preventDefault();
		var movie =$("#search").val();
		//var year = get year from DataBase
		var moviePoster = "";
		var movieTitle = "";
		var movieYear = "";
		var movieActor = "";
		var movieRating= "";
		var imdbLink = "";
		var url = "http://www.omdbapi.com/?apikey=a0784ce7";
		$.ajax({
			method : 'get',
			url: url + "&t=" + movie /*+ "&y=" + year*/,
			success: function(data){
				console.log(data);
				
				moviePoster = `
<img alt="${data.Title}" width="85%" src="${data.Poster}"/>
`;
				movieTitle =`
<h3> Movie: ${data.Title}</h3>
`;
				movieRating =`
<h4> Rated: ${data.Rated}</h4>
`;
				movieYear =`
<h4> Release Year: ${data.Year}</h4>
`;
				movieActor=`
<h4> Actors: ${data.Actors}</h4>
`;
				imdbLink=`
<a href="https://www.imdb.com/title/${data.imdbID}"><img width="25%" src="../images/IMDB.png" /></a>
`;
				$("#title").html(movieTitle);
				$("#poster").html(moviePoster);

				$("#releaseYear").html(movieYear);
				$("#rating").html(movieRating);
				$("#imdbLink").html(imdbLink);
				$("#actor").html(movieActor);
				
			}
		})
	})
		});