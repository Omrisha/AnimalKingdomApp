package com.example.animalskingdom.Services

import com.example.animalskingdom.Models.PhotoSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val FLICKR_API_KEY = "287542f94f253f5f8bd38ac9bd459034"
interface ImageService {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&tags=animal,animals&api_key=$FLICKR_API_KEY")
    fun fetchImages(@Query("text") animalName: String): Call<PhotoSearchResponse>
}