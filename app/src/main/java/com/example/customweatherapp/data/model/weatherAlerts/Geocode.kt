package com.example.customweatherapp.data.model.weatherAlerts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geocode(
    @SerialName("SAME")
    val specificAreaMessageEncodingList: List<String>,
    @SerialName("UGC")
    val universalGeographicCodeList: List<String>
)