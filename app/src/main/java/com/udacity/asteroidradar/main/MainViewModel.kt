package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.DailyImage
import com.udacity.asteroidradar.api.AsteroidApi
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

    private val _dailyImage = MutableLiveData<DailyImage>()
    val dailyImage: LiveData<DailyImage>
        get() = _dailyImage

    init {
        val sdf = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
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
                _asteriodData.value = parseAsteroidsJsonResult(JSONObject(response.body().toString()))
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("MainViewModel", "Failure: ${t.message}")
            }

        })
    }
    private fun getApod() {
        AsteroidApi.retrofitService.getApod(Constants.API_KEY).enqueue( object : Callback<DailyImage> {
            override fun onResponse(call: Call<DailyImage>, response: Response<DailyImage>) {
                _dailyImage.value = response.body()
            }

            override fun onFailure(call: Call<DailyImage>, t: Throwable) {
                Log.d("MainViewModel", "Failure: ${t.message}")
            }
        })
    }

}