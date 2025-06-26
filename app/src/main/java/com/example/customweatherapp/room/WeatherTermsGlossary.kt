package com.example.customweatherapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.customweatherapp.data.model.weatherTermsGlossary.Glossary

@Entity(tableName = "weather_terms_glossary")
data class WeatherTermsGlossary (
    @PrimaryKey(autoGenerate = true)
    var identifier: Int = 0,
    var term: String = "",
    var definition: String = ""
)