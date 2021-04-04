package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.DailyImage
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidApiStatus
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetail.value = asteroid
    }

    fun onAsteroidNavigated() {
        _navigateToDetail.value = null
    }

    private val _asteriodStatus = MutableLiveData<AsteroidApiStatus>()
    val asteriodStatus: LiveData<AsteroidApiStatus>
        get() = _asteriodStatus

    private val _navigateToDetail = MutableLiveData<Asteroid>()
    val navigateToDetail
        get() = _navigateToDetail

    private val _dailyImage = MutableLiveData<DailyImage>()
    val dailyImage: LiveData<DailyImage>
        get() = _dailyImage

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    init {
        getApod()
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }

    fun getWeekDate() : String {
        val calendar = Calendar.getInstance()
        val currentDayNumber = calendar.get(Calendar.DAY_OF_WEEK)
        val difference = Constants.NUMBER_OF_DAYS_IN_WEEK - currentDayNumber
        calendar.add(Calendar.DAY_OF_YEAR, difference)
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val getDate = dateFormat.format(calendar.time)
        return getDate
    }
    //val asteriodData = asteroidRepository.weekAsteroids(getWeekDate())
    val asteriodData = asteroidRepository.asteroids
    //val asteriodData = asteroidRepository.todayAsteroids


    private fun getApod() {
        viewModelScope.launch {
            try {
                val downloadedImage = AsteroidApi.retrofitService.getApod(Constants.API_KEY)
                if (downloadedImage.mediaType.toLowerCase(Locale.getDefault()) == Constants.IMAGE_KEY) {
                    _dailyImage.value = downloadedImage
                } else {
                    _dailyImage.value = null
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Apod Failure: ${e.message}")
                _dailyImage.value = null
            }
        }
    }

}