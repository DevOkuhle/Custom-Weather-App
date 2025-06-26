package com.example.customweatherapp.data.model.allAlertTypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class AllAlertTypesResponse(
    @SerialName("@context")
    var context: JsonElement? = null,
    var eventTypes: List<String> = emptyList()
)