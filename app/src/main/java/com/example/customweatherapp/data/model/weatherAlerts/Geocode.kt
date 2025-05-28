package com.example.customweatherapp.data.model.weatherAlerts

import com.fasterxml.jackson.annotation.JsonProperty

data class Geocode(
    @JsonProperty("SAME")
    var specificAreaMessageEncodingList: List<String> = emptyList(),
    @JsonProperty("UGC")
    var universalGeographicCodeList: List<String> = emptyList()
)