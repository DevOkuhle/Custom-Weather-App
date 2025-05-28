package com.example.customweatherapp.data.model.forecastByGridPoints.request

data class GridPointsForecastRequest(
    var weatherForecastOfficeID: String = "",
    var longitudeCoordinate: Int = -1,
    var latitudeCoordinate: Int = -1,
    var unitOfMeasurements: String
)
