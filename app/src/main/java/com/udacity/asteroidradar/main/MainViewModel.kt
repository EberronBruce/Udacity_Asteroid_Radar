package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseApodJsonResult
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*



class MainViewModel : ViewModel() {
    private var _asteriodData = MutableLiveData<List<Asteroid>>()
    val asteriodData: LiveData<List<Asteroid>>
        get() = _asteriodData

    private val _navigateToDetail = MutableLiveData<Asteroid>()
    val navigateToDetail
        get() = _navigateToDetail

    private val _dailyImageUrl = MutableLiveData<Map<String, String>>()
    val dailyImageUrl: LiveData<Map<String, String>>
        get() = _dailyImageUrl

    init {
        Log.d("MainViewModel", "----------- Init -------------")
        val sdf = SimpleDateFormat("yyyy-mm-dd")
        val currentDate = sdf.format(Date())
        getAsteroids(currentDate)
        getApod()
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetail.value = asteroid
    }

    fun onAsteroidNavigated() {
        _navigateToDetail.value = null
    }

    private fun getAsteroids(date: String) {
        AsteroidApi.retrofitService.getAsteroids(date, Constants.API_KEY).enqueue( object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                //Log.d("MainViewModel", "Body: ${response.body()}")
                var asteroids = parseAsteroidsJsonResult(JSONObject(response.body().toString()))
                _asteriodData.value = asteroids
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("MainViewModel", "Failure: ${t.message}")
            }

        })
    }
    private fun getApod() {
        AsteroidApi.retrofitService.getApod(Constants.API_KEY).enqueue( object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("MainViewModel", "${response.raw().request().url()}")
                val imgInfoMap = parseApodJsonResult(JSONObject(response.body().toString()))
                if (imgInfoMap != null) {
                    _dailyImageUrl.value = imgInfoMap
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("MainViewModel", "Failure: ${t.message}")
            }
        })
    }

}