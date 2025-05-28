package com.example.customweatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customweatherapp.data.api.APICallResult
import com.example.customweatherapp.data.api.FailureResponse
import com.example.customweatherapp.data.model.allAlertTypes.AllAlertTypesResponse
import com.example.customweatherapp.data.model.forecastByGridPoints.request.GridPointsForecastRequest
import com.example.customweatherapp.data.model.forecastByGridPoints.response.WeatherForecastByGridPointsResponse
import com.example.customweatherapp.data.model.weatherAlerts.WeatherAlertsResponse
import com.example.customweatherapp.data.model.weatherTermsGlossary.WeatherTermsGlossaryResponse
import com.example.customweatherapp.repository.CustomWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomWeatherViewModel @Inject constructor(private val customWeatherRepository: CustomWeatherRepository): ViewModel() {
    private val _allAlertTypesMutableStateFlow = MutableStateFlow(AllAlertTypesResponse())
    val allAlertTypesMutableStateFlow = _allAlertTypesMutableStateFlow.asStateFlow()

    private val _weatherTermsGlossaryMutableStateFlow = MutableStateFlow(WeatherTermsGlossaryResponse())
    val weatherTermsGlossaryMutableStateFlow = _weatherTermsGlossaryMutableStateFlow.asStateFlow()

    private val _alertByAreaCodeMutableStateFlow = MutableStateFlow(WeatherAlertsResponse())
    val alertByAreaCodeMutableStateFlow = _alertByAreaCodeMutableStateFlow.asStateFlow()

    private val _alertByZoneIdentifierMutableStateFlow = MutableStateFlow(WeatherAlertsResponse())
    val alertByZoneIdentifierMutableStateFlow = _alertByZoneIdentifierMutableStateFlow.asStateFlow()

    private val _alertByRegionMutableStateFlow = MutableStateFlow(WeatherAlertsResponse())
    val alertByRegionMutableStateFlow = _alertByRegionMutableStateFlow.asStateFlow()

    private val _weatherForecastByGridPointsMutableStateFlow = MutableStateFlow(WeatherForecastByGridPointsResponse())
    val weatherForecastByGridPointsMutableStateFlow = _weatherForecastByGridPointsMutableStateFlow.asStateFlow()

    private val _weatherForecastByGridPointsHourlyMutableStateFlow = MutableStateFlow(WeatherForecastByGridPointsResponse())
    val weatherForecastByGridPointsHourlyMutableStateFlow = _weatherForecastByGridPointsHourlyMutableStateFlow.asStateFlow()

    private val _failureResponseMutableStateFlow = MutableStateFlow(FailureResponse())
    val failureResponseMutableStateFlow = _weatherTermsGlossaryMutableStateFlow.asStateFlow()

    fun getWeatherTermsGlossary() =
        invokeAPICallsAndPopulateObservables(_weatherTermsGlossaryMutableStateFlow) { customWeatherRepository.getWeatherTermsGlossary() }

    fun getAlertByAreaCode(areaCode: String) =
        invokeAPICallsAndPopulateObservables(_alertByAreaCodeMutableStateFlow) { customWeatherRepository.getAlertByAreaCode(areaCode) }

    fun getAlertByZoneIdentifier(zoneId: String) =
        invokeAPICallsAndPopulateObservables(_alertByZoneIdentifierMutableStateFlow) { customWeatherRepository.getAlertByZoneIdentifier(zoneId) }

    fun getAlertByRegion(region: String) =
        invokeAPICallsAndPopulateObservables(_alertByRegionMutableStateFlow) { customWeatherRepository.getAlertByRegion(region) }

    fun getWeatherForecastByGridPoints(gridPointsForecastRequest: GridPointsForecastRequest) =
        invokeAPICallsAndPopulateObservables(_weatherForecastByGridPointsMutableStateFlow) { customWeatherRepository.getWeatherForecastByGridPoints(gridPointsForecastRequest) }

    fun getWeatherForecastByGridPointsHourly(gridPointsForecastRequest: GridPointsForecastRequest) =
        invokeAPICallsAndPopulateObservables(_weatherForecastByGridPointsMutableStateFlow) { customWeatherRepository.getWeatherForecastByGridPointsHourly(gridPointsForecastRequest) }


    private fun <T> invokeAPICallsAndPopulateObservables(customWeatherMutableStateFlow: MutableStateFlow<T>, apiWeatherInvocation: suspend() -> Flow<APICallResult<T>>) {
        viewModelScope.launch {
            apiWeatherInvocation().collectLatest { response ->
                when (response) {
                    is APICallResult.Failure -> {
                        response.failureResponse?.let { failureResponse ->
                            _failureResponseMutableStateFlow.update { failureResponse }
                        }
                    }

                    is APICallResult.Success -> {
                        response.successResponse?.let { weatherTermsGlossaryResponse ->
                            customWeatherMutableStateFlow.update { weatherTermsGlossaryResponse }
                        }
                    }
                }
            }
        }
    }
}