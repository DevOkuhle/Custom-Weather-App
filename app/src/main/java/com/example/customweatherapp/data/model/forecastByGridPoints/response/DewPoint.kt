package com.example.customweatherapp.data.model.forecastByGridPoints.response

import kotlinx.serialization.Serializable

@Serializable
data class DewPoint(
    var unitCode: String = "",
    var value: Double = -1.0
)