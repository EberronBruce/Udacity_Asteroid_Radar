package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.DailyImage
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
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
        viewModelScope.launch {
            try {
                val jsonResponse = JSONObject(AsteroidApi.retrofitService.getAsteroids(date, Constants.API_KEY))
                _asteriodData.value = parseAsteroidsJsonResult(jsonResponse)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Asteroid Failure: ${e.message}")
            }
        }
    }
    
    private fun getApod() {
        viewModelScope.launch {
            try {
                _dailyImage.value = AsteroidApi.retrofitService.getApod(Constants.API_KEY)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Apod Failure: ${e.message}")
            }
        }
    }

}