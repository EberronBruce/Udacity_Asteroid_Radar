package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

private const val API_KEY = "CHII60uETgFUbqsFXMCTjx3KxpzYdaFpaUPHe51Z"

class MainViewModel : ViewModel() {
    private var _asteriodData = MutableLiveData<List<Asteroid>>()
    val asteriodData: LiveData<List<Asteroid>>
        get() = _asteriodData

    private val _navigateToDetail = MutableLiveData<Asteroid>()
    val navigateToDetail
        get() = _navigateToDetail

    init {
        Log.d("MainViewModel", "----------- Init -------------")
        val sdf = SimpleDateFormat("yyyy-mm-dd")
        val currentDate = sdf.format(Date())
        getAsteroids(currentDate)
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetail.value = asteroid
    }

    fun onAsteroidNavigated() {
        _navigateToDetail.value = null
    }

    private fun getAsteroids(date: String) {
        AsteroidApi.retrofitService.getAsteroids(date, API_KEY).enqueue( object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("MainViewModel", "Headers: ${response.headers()}")
                Log.d("MainViewModel", "Body: ${response.body()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("MainViewModel", "Failure: ${t.message}")
            }

        })
    }

}