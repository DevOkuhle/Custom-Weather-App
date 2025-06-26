package com.example.customweatherapp.data.model.forecastByGridPoints.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Geometry(
    var coordinates: JsonElement? = null,
    var type: String = "",
    @SerialName("bbox")
    var box: JsonElement? = null
)