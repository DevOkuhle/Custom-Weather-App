package com.example.customweatherapp.data.model.forecastByGridPoints.response

import kotlinx.serialization.Serializable

@Serializable
data class Properties(
    var elevation: DewPoint = DewPoint(),
    var forecastGenerator: String = "",
    var generatedAt: String = "",
    var periods: List<Period> = emptyList(),
    var units: String = "",
    var updateTime: String = "",
    var validTimes: String = ""
)