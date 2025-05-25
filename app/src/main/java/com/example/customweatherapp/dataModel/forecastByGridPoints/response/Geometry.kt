package com.example.customweatherapp.dataModel.forecastByGridPoints.response

data class Geometry(
    var coordinates: List<List<List<Double>>> = emptyList(),
    var type: String = ""
)