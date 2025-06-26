package com.example.customweatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.example.customweatherapp.R
import com.example.customweatherapp.data.model.ComposableFunctionAttributes
import com.example.customweatherapp.data.model.CustomWeatherGenericCardAttributes
import com.example.customweatherapp.ui.navigation.CustomWeatherNavigationScreen
import com.example.customweatherapp.util.PopulateCardForAlertOrForecast
import com.example.customweatherapp.util.SetUpScaffoldTopBar
import com.example.customweatherapp.util.ShareCustomWeatherObjects.selectedHourlyForecastLocation

@Composable
fun ChooseWeatherForecastOptions(composableFunctionAttributes: ComposableFunctionAttributes) = with(composableFunctionAttributes) {
    Scaffold(modifier = modifier.fillMaxSize(),
    topBar = {
        SetUpScaffoldTopBar(composableFunctionAttributes)
    }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            PopulateCardForAlertOrForecast(
                modifier,
                false,
                CustomWeatherGenericCardAttributes(
                    isForecastHourly = false,
                    forecastButtonLabel = stringResource(R.string.view_weather_forecast_button_label),
                    generalOrHourlyForecastList = stringArrayResource(R.array.forecastOfficeAndCity).toList(),
                    generalOrHourlyForecastTitle = stringResource(R.string.view_weather_forecast_by_forecast_office),
                    generalOrHourlyForecastDescription = stringResource(R.string.view_weather_forecast_description),
                    metricUnitsList = stringArrayResource(R.array.metricUnitsConversion).toList()
                )
            ) {
                navigationController.navigate(route = CustomWeatherNavigationScreen.ViewWeatherForecastScreen.route)
            }
            PopulateCardForAlertOrForecast(
                modifier,
                false,
                CustomWeatherGenericCardAttributes(
                    isForecastHourly = true,
                    forecastButtonLabel = stringResource(R.string.view_weather_forecast_hourly_button_label),
                    generalOrHourlyForecastList = stringArrayResource(R.array.forecastOfficeAndCity).toList(),
                    generalOrHourlyForecastTitle = stringResource(R.string.view_hourly_weather_forecast_by_forecast_office),
                    generalOrHourlyForecastDescription = stringResource(R.string.view_hourly_weather_forecast_description),
                    metricUnitsList = stringArrayResource(R.array.metricUnitsConversion).toList()
                )
            ) {
                navigationController.navigate(route = CustomWeatherNavigationScreen.ViewWeatherForecastHourlyScreen.route)
            }
        }
    }
}