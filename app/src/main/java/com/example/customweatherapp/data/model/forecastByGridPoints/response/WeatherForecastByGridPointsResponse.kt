package com.example.customweatherapp.data.model.forecastByGridPoints.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class WeatherForecastByGridPointsResponse(
    @SerialName("@context")
    var context: JsonElement? = null,
    var geometry: Geometry = Geometry(),
    var properties: Properties = Properties(),
    var type: String = ""
)