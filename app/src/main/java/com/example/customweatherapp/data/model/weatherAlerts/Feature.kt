package com.example.customweatherapp.data.model.weatherAlerts

import com.fasterxml.jackson.annotation.JsonProperty

data class Feature(
    var geometry: List<List<List<Double>>>? = null,
    @JsonProperty("id")
    var identifier: String = "",
    @JsonProperty("properties")
    var weatherProperties: WeatherProperties = WeatherProperties(),
    var type: String = ""
)