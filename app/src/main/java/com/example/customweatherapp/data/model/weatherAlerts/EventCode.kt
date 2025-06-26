package com.example.customweatherapp.data.model.weatherAlerts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventCode(
    @SerialName("SAME")
    val specificAreaMessageEncodingList: List<String>,
    @SerialName("NationalWeatherService")
    val nationalWeatherService: List<String>
)