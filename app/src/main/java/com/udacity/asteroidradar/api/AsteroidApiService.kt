package com.udacity.asteroidradar.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val APOD_URL = "https://api.nasa.gov/planetary/apod"
// https://api.nasa.gov/neo/rest/v1/feed?start_date=START_DATE&end_date=END_DATE&api_key=API_KEY
private const val ASTEROID_BASE_URL = "https://api.nasa.gov"



private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(ASTEROID_BASE_URL)
    .build()

interface AsteroidApiService {
    @GET("/neo/rest/v1/feed")
    fun getAsteroids( @Query("START_DATE") start: String, @Query("API_KEY") key: String):
            Call<String>
}

object AsteroidApi {
    val retrofitService : AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}