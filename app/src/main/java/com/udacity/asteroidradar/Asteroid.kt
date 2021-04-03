package com.udacity.asteroidradar

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable

@Parcelize
data class DailyImage(val title: String,
                      @Json(name = "media_type")
                      val mediaType: String,
                      val url: String) : Parcelable