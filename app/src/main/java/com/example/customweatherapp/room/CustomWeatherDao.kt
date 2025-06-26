package com.example.customweatherapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomWeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAlertTypes(alertTypes: List<AlertTypes>)

    @Query("SELECT * FROM alert_types ORDER BY identifier ASC")
    fun readAllAlertTypes(): Flow<List<AlertTypes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWeatherGlossary(weatherTermsGlossary: List<WeatherTermsGlossary>)

    @Query("SELECT * FROM weather_terms_glossary ORDER BY identifier ASC")
    fun readAllWeatherTermsGlossary(): Flow<List<WeatherTermsGlossary>>

    @Query("SELECT COUNT(*) > 5 FROM alert_types")
    fun isAlertTypesTableNotEmpty(): Flow<Boolean>

    @Query("SELECT COUNT(*) > 5 FROM weather_terms_glossary")
    fun isWeatherTermsGlossaryTableNotEmpty(): Flow<Boolean>
}