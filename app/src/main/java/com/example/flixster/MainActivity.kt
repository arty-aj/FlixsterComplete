package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

//Request URL
private const val NOW_PLAYING_URL ="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val movies = mutableListOf<Movie>()

    private lateinit var rvMovies: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMovies = findViewById(R.id.rvMovies)


        val movieAdapter = MovieAdapter(this, movies)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        //creating a new object of the library httpClient
        val client = AsyncHttpClient()
        //First param is request URL,second is response handler
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler() {
            //if fails, we will see this tag, usually means URL is bad
            override fun onFailure(
                statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode")
            }

            //? means it is nullable
            //Nullable means that a return type can be Null
            //if works, it will show this tag, means the data passed by is good data
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess JSON data $json")

                try {
                    //parse out the data and list out the movies
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    //make a data class our hierarchy
                    //want to add our movieJasonArray we just got
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie List $movies")
                }catch(e: JSONException){
                    Log.e(TAG, "enocountered Exception $e")
                }
            }

        })
    }
}