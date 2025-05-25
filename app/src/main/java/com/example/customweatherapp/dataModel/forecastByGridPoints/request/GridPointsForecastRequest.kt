package com.example.customweatherapp.dataModel.forecastByGridPoints.request

data class GridPointsForecastRequest(
    var weatherForecastOfficeID: String = "",
    var longitudeCoordinate: Int = -1,
    var latitudeCoordinate: Int = -1
)
