package com.example.customweatherapp.data.model.weatherAlerts

import com.fasterxml.jackson.annotation.JsonProperty

data class EventCode(
    @JsonProperty("NationalWeatherService")
    var nationalWeatherServiceList: List<String> = emptyList(),
    @JsonProperty("SAME")
    var specificAreaMessageEncodingList: List<String> = emptyList()
)