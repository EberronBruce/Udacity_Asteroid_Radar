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
import kotlin.collections.ArrayList

enum class AsteriodApiStatus { LOADING, ERROR, DONE }

class MainViewModel : ViewModel() {

    private val _asteriodStatus = MutableLiveData<AsteriodApiStatus>()
    val asteriodStatus: LiveData<AsteriodApiStatus>
        get() = _asteriodStatus

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
        getAsteroids(SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(Date()))
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
            _asteriodStatus.value = AsteriodApiStatus.LOADING
            try {
                val jsonResponse = JSONObject(AsteroidApi.retrofitService.getAsteroids(date, Constants.API_KEY))
                _asteriodData.value = parseAsteroidsJsonResult(jsonResponse)
                _asteriodStatus.value = AsteriodApiStatus.DONE
            } catch (e: Exception) {
                Log.e("MainViewModel", "Asteroid Failure: ${e.message}")
                _asteriodStatus.value = AsteriodApiStatus.ERROR
                _asteriodData.value = ArrayList()
            }
        }
    }

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