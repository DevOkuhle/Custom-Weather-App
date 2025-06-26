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
import com.example.customweatherapp.util.ShareCustomWeatherObjects.magnitudeConversions
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousFailureResponse
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousForecastInformation
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousWeatherForecastByGridPointsResponse
import com.example.customweatherapp.util.ShareCustomWeatherObjects.selectedForecastLocation
import com.example.customweatherapp.util.evaluateIfFailureResponseMutableStateFlowIsNotCached
import com.example.customweatherapp.viewModel.CustomWeatherViewModel

@Composable
fun ViewWeatherForecast(composableFunctionAttributes: ComposableFunctionAttributes, customWeatherViewModel: CustomWeatherViewModel) = with(composableFunctionAttributes) {
    var loading by remember { mutableStateOf(true) }
    val officeIdAndGridPoint = selectedForecastLocation.split(";")
    val coordinates = officeIdAndGridPoint.last().split(":")
    val officeIdAndCity = officeIdAndGridPoint.first().split("|")

    Scaffold(modifier = modifier.fillMaxWidth(),
        topBar = {
            SetUpScaffoldTopBar(composableFunctionAttributes)
        }
    ) { innerPadding ->
        if (loading) {
            DisplayCircularProgressIndicator(innerPadding)
            customWeatherViewModel.getWeatherForecastByGridPoints(GridPointsForecastRequest(
                weatherForecastOfficeID = officeIdAndCity.first().trim(),
                longitudeCoordinate = coordinates.first().trim().toInt(),
                latitudeCoordinate = coordinates.last().trim().toInt(),
                unitOfMeasurements = magnitudeConversions.lowercase()
                )
            )
            weatherForecastByGridPointsResponse = customWeatherViewModel.weatherForecastByGridPointsMutableStateFlow.collectAsState().value
            failureResponse = customWeatherViewModel.failureResponseMutableStateFlow.collectAsState().value
            customWeatherViewModel.isWeatherAPISuccessful?.let { isWeatherAPISuccessful ->
                if (isWeatherAPISuccessful && weatherForecastByGridPointsResponse.properties.periods.isNotEmpty() && evaluateIfForecastMutableStateFlowIsNotCached(weatherForecastByGridPointsResponse, selectedForecastLocation)) {
                    loading = false
                } else if (failureResponse.detail.isNotEmpty() && evaluateIfFailureResponseMutableStateFlowIsNotCached(previousForecastInformation, selectedForecastLocation, failureResponse)) {
                    previousForecastInformation = selectedForecastLocation
                    previousFailureResponse = failureResponse
                    HandleFailureEvents(composableFunctionAttributes)
                }
            }
        } else {
            PopulateWeatherForecastResponseCard(modifier, weatherForecastByGridPointsResponse, innerPadding, officeIdAndCity.last().trim(), false)
            previousForecastInformation = selectedForecastLocation
            previousWeatherForecastByGridPointsResponse = weatherForecastByGridPointsResponse
        }
    }
}

fun evaluateIfForecastMutableStateFlowIsNotCached(weatherForecastByGridPointsResponse: WeatherForecastByGridPointsResponse, selectedForecastOfficeId: String): Boolean {
    return (previousForecastInformation.isEmpty() || (previousWeatherForecastByGridPointsResponse == weatherForecastByGridPointsResponse && previousForecastInformation == selectedForecastOfficeId) || (previousWeatherForecastByGridPointsResponse != weatherForecastByGridPointsResponse && previousForecastInformation != selectedForecastOfficeId))
}