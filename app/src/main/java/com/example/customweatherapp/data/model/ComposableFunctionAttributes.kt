package com.example.customweatherapp.data.model

import WeatherAlertsResponse
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.customweatherapp.data.model.forecastByGridPoints.response.WeatherForecastByGridPointsResponse

data class ComposableFunctionAttributes(
    val modifier: Modifier,
    val navigationController: NavHostController,
    var alertAreaCode: String = "",
    var alertRegionalCode: String = "",
    var weatherAlertsResponse: WeatherAlertsResponse = WeatherAlertsResponse(),
    var weatherForecastByGridPointsResponse: WeatherForecastByGridPointsResponse = WeatherForecastByGridPointsResponse()
)