package com.example.customweatherapp.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.customweatherapp.data.model.ComposableFunctionAttributes
import com.example.customweatherapp.data.model.forecastByGridPoints.request.GridPointsForecastRequest
import com.example.customweatherapp.data.model.forecastByGridPoints.response.WeatherForecastByGridPointsResponse
import com.example.customweatherapp.util.DisplayCircularProgressIndicator
import com.example.customweatherapp.util.HandleFailureEvents
import com.example.customweatherapp.util.PopulateWeatherForecastResponseCard
import com.example.customweatherapp.util.SetUpScaffoldTopBar
import com.example.customweatherapp.util.ShareCustomWeatherObjects.failureResponse
import com.example.customweatherapp.util.ShareCustomWeatherObjects.magnitudeConversionsForHourlyForecast
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousFailureResponse
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousHourlyForecastInformation
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousHourlyWeatherForecastByGridPointsResponse
import com.example.customweatherapp.util.ShareCustomWeatherObjects.selectedHourlyForecastLocation
import com.example.customweatherapp.util.evaluateIfFailureResponseMutableStateFlowIsNotCached
import com.example.customweatherapp.viewModel.CustomWeatherViewModel

@Composable
fun ViewWeatherForecastHourly(composableFunctionAttributes: ComposableFunctionAttributes, customWeatherViewModel: CustomWeatherViewModel) = with(composableFunctionAttributes) {
    var loading by remember { mutableStateOf(true) }
    val officeIdAndGridPoint = selectedHourlyForecastLocation.split(";")
    val coordinates = officeIdAndGridPoint.last().split(":")
    val officeIdAndCity = officeIdAndGridPoint.first().split("|")

    Scaffold(modifier = modifier.fillMaxWidth(),
        topBar = {
            SetUpScaffoldTopBar(composableFunctionAttributes)
        }
    ) { innerPadding ->
        if (loading) {
            DisplayCircularProgressIndicator(innerPadding)
            customWeatherViewModel.getWeatherForecastByGridPointsHourly(GridPointsForecastRequest(
                    weatherForecastOfficeID = officeIdAndCity.first().trim(),
                    longitudeCoordinate = coordinates.first().trim().toInt(),
                    latitudeCoordinate = coordinates.last().trim().toInt(),
                    unitOfMeasurements = magnitudeConversionsForHourlyForecast.lowercase()
                )
            )
            weatherForecastByGridPointsResponse = customWeatherViewModel.weatherForecastByGridPointsHourlyMutableStateFlow.collectAsState().value
            failureResponse = customWeatherViewModel.failureResponseMutableStateFlow.collectAsState().value
            customWeatherViewModel.isWeatherAPISuccessful?.let { isWeatherAPISuccessful ->
                if (isWeatherAPISuccessful && weatherForecastByGridPointsResponse.properties.periods.isNotEmpty() && evaluateIfForecastHourlyMutableStateFlowIsNotCached(weatherForecastByGridPointsResponse, selectedHourlyForecastLocation)) {
                    loading = false
                } else if (failureResponse.detail.isNotEmpty() && evaluateIfFailureResponseMutableStateFlowIsNotCached(previousHourlyForecastInformation, selectedHourlyForecastLocation, failureResponse)) {
                    previousHourlyForecastInformation = selectedHourlyForecastLocation
                    previousFailureResponse = failureResponse
                    HandleFailureEvents(composableFunctionAttributes)
                }
            }
        } else {
            PopulateWeatherForecastResponseCard(modifier, weatherForecastByGridPointsResponse, innerPadding, officeIdAndCity.last().trim(), true)
            previousHourlyForecastInformation = selectedHourlyForecastLocation
            previousHourlyWeatherForecastByGridPointsResponse = weatherForecastByGridPointsResponse
        }
    }
}

fun evaluateIfForecastHourlyMutableStateFlowIsNotCached(weatherForecastByGridPointsResponse: WeatherForecastByGridPointsResponse, selectedForecastOfficeId: String): Boolean {
    return (previousHourlyForecastInformation.isEmpty() || (previousHourlyWeatherForecastByGridPointsResponse == weatherForecastByGridPointsResponse && previousHourlyForecastInformation == selectedForecastOfficeId) || (previousHourlyWeatherForecastByGridPointsResponse != weatherForecastByGridPointsResponse && previousHourlyForecastInformation != selectedForecastOfficeId))
}