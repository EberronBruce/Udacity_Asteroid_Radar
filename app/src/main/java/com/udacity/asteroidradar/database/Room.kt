package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AsteroidDao {
    @Query("SELECT * FROM databaseasteriod ORDER BY closeApproachDate ASC ")
    fun getAsteroids(): LiveData<List<DatabaseAsteriod>>

    @Query("SELECT * FROM databaseasteriod WHERE closeApproachDate = CURRENT_DATE")
    fun getTodayAsteroids(): LiveData<List<DatabaseAsteriod>>

    @Query("SELECT * FROM databaseasteriod WHERE closeApproachDate <= :date ORDER BY closeApproachDate ASC")
    fun getWeekAsteroids(date: String): LiveData<List<DatabaseAsteriod>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteriod)
}

@Database(entities = [DatabaseAsteriod::class], version = 1)
abstract class AsteroidDatabase: RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            AsteroidDatabase::class.java,
            "asteriods").build()
        }
    }
    return INSTANCE
}