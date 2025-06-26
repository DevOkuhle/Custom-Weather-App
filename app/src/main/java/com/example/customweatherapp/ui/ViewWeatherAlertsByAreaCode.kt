package com.example.customweatherapp.ui

import WeatherAlertsResponse
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.customweatherapp.R
import com.example.customweatherapp.data.model.ComposableFunctionAttributes
import com.example.customweatherapp.util.DisplayCircularProgressIndicator
import com.example.customweatherapp.util.HandleFailureEvents
import com.example.customweatherapp.util.PopulateEachAlertResponseCard
import com.example.customweatherapp.util.SetUpScaffoldTopBar
import com.example.customweatherapp.util.ShareCustomWeatherObjects.failureResponse
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousAreaCode
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousAreaCodeWeatherAlertsResponse
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousFailureResponse
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousForecastInformation
import com.example.customweatherapp.util.ShareCustomWeatherObjects.selectedForecastLocation
import com.example.customweatherapp.util.evaluateIfFailureResponseMutableStateFlowIsNotCached
import com.example.customweatherapp.util.verticalColumnScrollbar
import com.example.customweatherapp.viewModel.CustomWeatherViewModel

@Composable
fun ViewWeatherAlertsByAreaCode(composableFunctionAttributes: ComposableFunctionAttributes, customWeatherViewModel: CustomWeatherViewModel) = with(composableFunctionAttributes) {
    var loading by remember { mutableStateOf(true) }
    val areaCode = alertAreaCode.split("|").first().trim().substring(1)
    val scrollState = rememberScrollState()

    Scaffold(modifier = modifier.fillMaxWidth(),
        topBar = {
            SetUpScaffoldTopBar(composableFunctionAttributes)
        }
    ) { innerPadding ->
        if (loading) {
            DisplayCircularProgressIndicator(innerPadding)
            customWeatherViewModel.getAlertByAreaCode(areaCode)
            weatherAlertsResponse = customWeatherViewModel.alertByAreaCodeMutableStateFlow.collectAsState().value
            failureResponse = customWeatherViewModel.failureResponseMutableStateFlow.collectAsState().value
            customWeatherViewModel.isWeatherAPISuccessful?.let { isWeatherAPISuccessful ->
                if (isWeatherAPISuccessful && weatherAlertsResponse.title.isNotEmpty() && evaluateIfAreaCodeMutableStateFlowIsNotCached(weatherAlertsResponse, areaCode)) {
                    loading = false
                } else if (failureResponse.detail.isNotEmpty() && evaluateIfFailureResponseMutableStateFlowIsNotCached(previousAreaCode, areaCode, failureResponse)) {
                    previousAreaCode = areaCode
                    previousFailureResponse = failureResponse
                    HandleFailureEvents(composableFunctionAttributes)
                }
            }
        } else {
            Column(
                modifier = modifier.padding(innerPadding)
                    .verticalColumnScrollbar(scrollState)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    modifier = modifier.padding(dimensionResource(R.dimen.padding_15dp)),
                    textAlign = TextAlign.Center,
                    text = weatherAlertsResponse.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                if (weatherAlertsResponse.features.isEmpty()) {
                    Text(
                        modifier = modifier.padding(dimensionResource(R.dimen.padding_15dp)),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.no_alerts_in_area),
                        style = MaterialTheme.typography.titleLarge
                    )
                } else {
                    weatherAlertsResponse.features.forEach { alertFeature ->
                        PopulateEachAlertResponseCard(modifier, alertFeature)
                    }
                }
            }
            previousAreaCode = areaCode
            previousAreaCodeWeatherAlertsResponse = weatherAlertsResponse
        }
    }
}

fun evaluateIfAreaCodeMutableStateFlowIsNotCached(weatherAlertsResponse: WeatherAlertsResponse, alertAreaCode: String): Boolean {
    return (previousAreaCode.isEmpty() || (previousAreaCodeWeatherAlertsResponse == weatherAlertsResponse && previousAreaCode == alertAreaCode) || (previousAreaCodeWeatherAlertsResponse != weatherAlertsResponse && previousAreaCode != alertAreaCode))
}