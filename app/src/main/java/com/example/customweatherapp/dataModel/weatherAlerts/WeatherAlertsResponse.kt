package com.example.customweatherapp.dataModel.weatherAlerts

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherAlertsResponse(
    @JsonProperty("@context")
    var context: List<Any>,
    var featuresList: List<Feature> = emptyList(),
    var title: String = "",
    var type: String = "",
    var updated: String = ""
)