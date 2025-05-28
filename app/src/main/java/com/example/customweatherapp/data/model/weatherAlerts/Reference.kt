package com.example.customweatherapp.data.model.weatherAlerts

import com.fasterxml.jackson.annotation.JsonProperty

data class Reference(
    @JsonProperty("@id")
    var id: String = "",
    var identifier: String = "",
    var sender: String = "",
    var sentDate: String = ""
)