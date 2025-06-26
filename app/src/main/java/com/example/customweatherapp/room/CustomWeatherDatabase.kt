package com.example.customweatherapp.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AlertTypes::class, WeatherTermsGlossary::class], version = 2, exportSchema = false)
abstract class CustomWeatherDatabase: RoomDatabase() {
    abstract fun customWeatherDao(): CustomWeatherDao
}