package com.example.customweatherapp.data.model.forecastByGridPoints.response

data class Geometry(
    var coordinates: List<List<List<Double>>> = emptyList(),
    var type: String = ""
)