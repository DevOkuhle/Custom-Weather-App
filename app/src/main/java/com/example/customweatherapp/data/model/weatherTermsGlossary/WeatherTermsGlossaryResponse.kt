package com.example.customweatherapp.data.model.weatherTermsGlossary

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class WeatherTermsGlossaryResponse(
    @SerialName("@context")
    var context: JsonElement? = null,
    var glossary: List<Glossary> = emptyList()
)