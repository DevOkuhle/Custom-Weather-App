package com.example.customweatherapp.dataModel.weatherTermsGlossary

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherTermsGlossaryResponse(
    @JsonProperty("@context")
    var context: List<Any> = emptyList(),
    var glossary: List<Glossary> = emptyList()
)