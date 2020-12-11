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
			break;
		case "actor":
			break;
		case "award":
			break;
		case "advanced":
			$.ajax({
				method : 'get',
				url : '/rest/search?year=' + searchByYear + '&award=' + searchByAward,
				success: function(data){
					for(var i = 0; i < data.length; i++){
						var dataAward = data[i];
						renderHTMLYear(dataAward);
					}
				}
			})
			break;
		case "genre":
			break;
		case "film":
			break;
	}
});	
	$.ajax({
		method : 'get',
		success: function(data){
		}
	})
	$.ajax({
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
						content.insertAdjacentHTML('afterend', movieContent);
					}else{
						content.insertAdjacentHTML('beforeend', movieContent);
					}
				}
			})

	}else{
		content.insertAdjacentHTML('beforeend', movieContent);
	}

//Display detailed information about movie
function renderHTMLMovie(data, externalData){
	document.getElementById("content").innerHTML = "";
	var movieContent = "";
	console.log(data);
	if(externalData.Response == "True"){
		if(data.poster == "N/A"){
		}else{
		}
	}else{
		movieContent = "<h4>" + externalData.Error + "</h4>"
	}

	content.insertAdjacentHTML('beforeend', movieContent);

function howToGuide(){
	document.getElementById("content").innerHTML = "";
	var checkBox = document.getElementById("howTo");
	if(checkBox.checked == true){
		document.getElementById("mySearch").style.display = "none";
		document.getElementById("howTo").style.display = "block";
	}else{
		document.getElementById("howTo").style.display = "none";
		document.getElementById("mySearch").style.display = "block";
	}
