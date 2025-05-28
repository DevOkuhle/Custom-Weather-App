package com.example.customweatherapp.data.model.weatherAlerts

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherAlertsResponse(
    @JsonProperty("@context")
    var alertContext: List<Any> = emptyList(),
    var featuresList: List<Feature> = emptyList(),
    var title: String = "",
    var type: String = "",
    var updated: String = ""
)