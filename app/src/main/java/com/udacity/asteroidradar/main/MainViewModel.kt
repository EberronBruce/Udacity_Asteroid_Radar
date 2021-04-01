package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var _asteriodData = MutableLiveData<MutableList<String>>()
    val asteriodData: LiveData<MutableList<String>>
        get() = _asteriodData

    init {
        _asteriodData.value = mutableListOf(
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six",
            "Seven",
            "Eight",
            "Nine",
            "Ten"
        )
    }

}