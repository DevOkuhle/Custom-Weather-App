package com.example.customweatherapp.data.model.allAlertTypes

import com.fasterxml.jackson.annotation.JsonProperty

data class AllAlertTypesResponse(
    @JsonProperty("@context")
    var context: List<Any> = emptyList(),
    var eventTypes: List<String> = emptyList()
)