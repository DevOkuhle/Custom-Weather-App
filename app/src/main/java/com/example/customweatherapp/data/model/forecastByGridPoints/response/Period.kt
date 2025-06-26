package com.example.customweatherapp.data.model.forecastByGridPoints.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Period(
    var detailedForecast: String = "",
    @SerialName("dewpoint")
    var dewPoint: DewPoint = DewPoint(),
    var endTime: String = "",
    var icon: String = "",
    var isDaytime: Boolean = false,
    var name: String = "",
    var number: Int = -1,
    var probabilityOfPrecipitation: DewPoint = DewPoint(),
    var relativeHumidity: DewPoint = DewPoint(),
    var shortForecast: String = "",
    var startTime: String = "",
    var temperature: Int = -1,
    var temperatureTrend: String = "",
    var temperatureUnit: String = "",
    var windDirection: String = "",
    var windSpeed: String = ""
)