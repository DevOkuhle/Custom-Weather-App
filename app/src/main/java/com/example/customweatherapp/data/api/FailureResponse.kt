package com.example.customweatherapp.data.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class FailureResponse(
    var type: String = "",
    var title: String = "",
    var status: Int = -1,
    var detail: String = "",
    var instance: String = "",
    var correlationId: String = "",
    var additionalProp1: JsonElement? = null
)
