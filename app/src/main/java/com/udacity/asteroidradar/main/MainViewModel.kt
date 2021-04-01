package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class MainViewModel : ViewModel() {

    private var _asteriodData = MutableLiveData<MutableList<Asteroid>>()
    val asteriodData: LiveData<MutableList<Asteroid>>
        get() = _asteriodData

    init {
        _asteriodData.value = mutableListOf(
            Asteroid(1, "one", "Apirl 1", 20.3, 40.5, 60.5, 1005.35,false),
            Asteroid(2, "two", "Oct 2", 20.3, 40.5, 60.5, 1005.35,true),
            Asteroid(3, "three", "Sept 3", 20.3, 40.5, 60.5, 1005.35,false),
            Asteroid(4, "four", "Jan 2", 20.3, 40.5, 60.5, 1005.35,false),
            Asteroid(5, "five", "Sept 1", 20.3, 40.5, 60.5, 1005.35,true),
            Asteroid(6, "six", "May 20", 20.3, 40.5, 60.5, 1005.35,false),
            Asteroid(7, "seven", "Feb 4", 20.3, 40.5, 60.5, 1005.35,false),
            Asteroid(8, "eight", "Oct 1", 20.3, 40.5, 60.5, 1005.35,false),
            Asteroid(9, "nine", "June 15", 20.3, 40.5, 60.5, 1005.35,true),
            Asteroid(10, "ten", "July 4", 20.3, 40.5, 60.5, 1005.35,false),
            Asteroid(11, "eleven", "June 3", 20.3, 40.5, 60.5, 1005.35,false),
            Asteroid(12, "twelve", "August 6", 20.3, 40.5, 60.5, 1005.35,false),
            Asteroid(13, "thirteen", "Nov 8", 20.3, 40.5, 60.5, 1005.35,false),
            Asteroid(14, "fourteen", "Dec 12", 20.3, 40.5, 60.5, 1005.35,false)
        )
    }

}