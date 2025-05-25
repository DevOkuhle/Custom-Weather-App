package com.example.customweatherapp.dataModel.forecastByGridPoints.response

data class Properties(
    var elevation: DewPoint = DewPoint(),
    var forecastGenerator: String = "",
    var generatedAt: String = "",
    var periods: List<Period> = emptyList(),
    var units: String = "",
    var updateTime: String = "",
    var validTimes: String = ""
)