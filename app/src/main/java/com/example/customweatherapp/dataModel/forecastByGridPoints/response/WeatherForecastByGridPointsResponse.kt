package com.example.customweatherapp.dataModel.forecastByGridPoints.response

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherForecastByGridPointsResponse(
    @JsonProperty("@context")
    var context: List<Any> = emptyList(),
    var geometry: Geometry = Geometry(),
    var properties: Properties = Properties(),
    var type: String = ""
)