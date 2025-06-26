package com.example.customweatherapp.data.model.weatherAlerts

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Feature(
    val id: String,
    val type: String,
    val geometry: JsonElement? = null,
    val properties: Properties
)