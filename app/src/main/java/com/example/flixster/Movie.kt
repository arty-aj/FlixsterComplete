package com.example.flixster

import org.json.JSONArray

//bundle up data
data class Movie(
    val movieId: Int,
    private val posterPath: String,
    val title: String,
    val overView: String
) {
    val posterImageURL = "https://image.tmdb.org/t/p/w342/$posterPath"

    //allows us to call methods on the movie class without defining an instance
    companion object{
        //firs : accepts JSONArrays as movieJsonArray
        //second : returns a mutable list of type Movie
        fun fromJsonArray(movieJsonArray: JSONArray) : List<Movie> {
            val movies = mutableListOf<Movie>()
            //for loop ex
            for(i in 0 until movieJsonArray.length()){
                //get the object in position i as it moves along
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    //call the movie consturctor that take the 4 parameters up top of code
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")
                    )
                )
            }
            return movies
        }

    }
}