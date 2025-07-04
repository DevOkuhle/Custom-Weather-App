package com.example.customweatherapp.viewModel

import WeatherAlertsResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customweatherapp.data.api.APICallResult
import com.example.customweatherapp.data.api.ParcelableFailureResponse
import com.example.customweatherapp.data.model.allAlertTypes.AllAlertTypesResponse
import com.example.customweatherapp.data.model.forecastByGridPoints.request.GridPointsForecastRequest
import com.example.customweatherapp.data.model.forecastByGridPoints.response.WeatherForecastByGridPointsResponse
import com.example.customweatherapp.data.model.weatherTermsGlossary.Glossary
import com.example.customweatherapp.data.model.weatherTermsGlossary.WeatherTermsGlossaryResponse
import com.example.customweatherapp.repository.CustomWeatherRepository
import com.example.customweatherapp.room.AlertTypes
import com.example.customweatherapp.room.WeatherTermsGlossary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CustomWeatherViewModel @Inject constructor(private val customWeatherRepository: CustomWeatherRepository): ViewModel() {

    private val _allAlertTypesMutableStateFlow = MutableStateFlow(AllAlertTypesResponse())
    val allAlertTypesMutableStateFlow = _allAlertTypesMutableStateFlow.asStateFlow()

    private val _weatherTermsGlossaryMutableStateFlow = MutableStateFlow(WeatherTermsGlossaryResponse())
    val weatherTermsGlossaryMutableStateFlow = _weatherTermsGlossaryMutableStateFlow.asStateFlow()

    private var _alertByAreaCodeMutableStateFlow = MutableStateFlow(WeatherAlertsResponse())
    var alertByAreaCodeMutableStateFlow = _alertByAreaCodeMutableStateFlow.asStateFlow()

    private val _alertByRegionMutableStateFlow = MutableStateFlow(WeatherAlertsResponse())
    val alertByRegionMutableStateFlow = _alertByRegionMutableStateFlow.asStateFlow()

    private val _weatherForecastByGridPointsMutableStateFlow = MutableStateFlow(WeatherForecastByGridPointsResponse())
    val weatherForecastByGridPointsMutableStateFlow = _weatherForecastByGridPointsMutableStateFlow.asStateFlow()

    private val _weatherForecastByGridPointsHourlyMutableStateFlow = MutableStateFlow(WeatherForecastByGridPointsResponse())
    val weatherForecastByGridPointsHourlyMutableStateFlow = _weatherForecastByGridPointsHourlyMutableStateFlow.asStateFlow()

    private val _failureResponseMutableStateFlow = MutableStateFlow(ParcelableFailureResponse())
    val failureResponseMutableStateFlow = _failureResponseMutableStateFlow.asStateFlow()

    private var isWeatherTypeGlossaryTableEmpty: Boolean? = null
    private var isAlertTypesTableEmpty: Boolean? = null
    var isWeatherAPISuccessful: Boolean? = null

    private fun getAllAlertTypes() =
        invokeAPICallsAndPopulateObservables(_allAlertTypesMutableStateFlow) { customWeatherRepository.getAllAlertTypes() }

    private fun getWeatherTermsGlossary() =
        invokeAPICallsAndPopulateObservables(_weatherTermsGlossaryMutableStateFlow) { customWeatherRepository.getWeatherTermsGlossary() }

    fun getAlertByAreaCode(areaCode: String) =
        invokeAPICallsAndPopulateObservables(_alertByAreaCodeMutableStateFlow) { customWeatherRepository.getAlertByAreaCode(areaCode) }

    fun getAlertByRegion(region: String) =
        invokeAPICallsAndPopulateObservables(_alertByRegionMutableStateFlow) { customWeatherRepository.getAlertByRegion(region) }

    fun getWeatherForecastByGridPoints(gridPointsForecastRequest: GridPointsForecastRequest) =
        invokeAPICallsAndPopulateObservables(_weatherForecastByGridPointsMutableStateFlow) { customWeatherRepository.getWeatherForecastByGridPoints(gridPointsForecastRequest) }

    fun getWeatherForecastByGridPointsHourly(gridPointsForecastRequest: GridPointsForecastRequest) =
        invokeAPICallsAndPopulateObservables(_weatherForecastByGridPointsHourlyMutableStateFlow) { customWeatherRepository.getWeatherForecastByGridPointsHourly(gridPointsForecastRequest) }

    private fun isReadWeatherTypeTableEmpty() {
        viewModelScope.launch {
             customWeatherRepository.isWeatherTermsGlossaryTableNotEmpty(viewModelScope).collectLatest {
                 isWeatherTypeGlossaryTableEmpty = it
             }
        }
    }

    private fun isAlertTypesTableEmpty() {
        viewModelScope.launch {
            customWeatherRepository.isAlertTypesTableNotEmpty(viewModelScope).collectLatest { isAlertTypesTableEmpty = it }
        }
    }

    private fun addAllWeatherTypes(alertTypes: List<AlertTypes>) {
        viewModelScope.launch {
            customWeatherRepository.addAlertTypes(alertTypes)
        }
    }

    private fun addAllWeatherTermsGlossary(weatherTermsGlossary: List<WeatherTermsGlossary>) {
        viewModelScope.launch {
            customWeatherRepository.addWeatherTermsGlossary(weatherTermsGlossary)
        }
    }

    fun readAlertTypesFromDatabase() {
        viewModelScope.launch {
            customWeatherRepository.readAllAlertTypes().collectLatest { alertTypes ->
                val alertTypesTerms = mutableListOf<String>()
                alertTypes.forEach { alertType ->
                    alertTypesTerms.add(alertType.alertType)
                }
                _allAlertTypesMutableStateFlow.update { AllAlertTypesResponse(eventTypes = alertTypesTerms) } }
        }
    }

    fun readWeatherTermsGlossaryFromDatabase() {
        viewModelScope.launch {
            val weatherTermsGlossaryList = mutableListOf<Glossary>()
            customWeatherRepository.readAllWeatherTermsGlossary().collectLatest { weatherTermsGlossary ->
                weatherTermsGlossary.forEach { weatherTerm ->
                    weatherTermsGlossaryList.add(Glossary(weatherTerm.term, weatherTerm.definition))
                }
                _weatherTermsGlossaryMutableStateFlow.update { WeatherTermsGlossaryResponse(glossary = weatherTermsGlossaryList) } }
        }
    }

    fun viewAlertTypesButtonClicked() {
        if (allAlertTypesMutableStateFlow.value.eventTypes.isEmpty()) {
            isAlertTypesTableEmpty()
            if (isAlertTypesTableEmpty == null || isAlertTypesTableEmpty == false) {
                getAllAlertTypes()
            } else {
                readAlertTypesFromDatabase()
            }
        }
    }

    fun viewWeatherTermsGlossaryButtonClicked() {
        if (weatherTermsGlossaryMutableStateFlow.value.glossary.isEmpty()) {
            isReadWeatherTypeTableEmpty()
            if (isWeatherTypeGlossaryTableEmpty == null || isWeatherTypeGlossaryTableEmpty == false) {
                getWeatherTermsGlossary()
            } else {
                readWeatherTermsGlossaryFromDatabase()
            }
        }
    }

    fun addAlertTypesToDatabase(alertTypes: List<String>) = runBlocking {
        if (isAlertTypesTableEmpty == null || isAlertTypesTableEmpty == false) {
            val alertTypesMutableList = mutableListOf<AlertTypes>()
            alertTypes.forEach { alertType ->
                alertTypesMutableList.add(AlertTypes(alertType = alertType))
            }
            addAllWeatherTypes(alertTypesMutableList)
        }
    }

    fun addWeatherTermsGlossaryToDatabase(weatherTypeGlossary: List<Glossary>) = runBlocking {
        if (isWeatherTypeGlossaryTableEmpty == null || isWeatherTypeGlossaryTableEmpty == false) {
            val weatherTermsGlossaryMutableList = mutableListOf<WeatherTermsGlossary>()
            weatherTypeGlossary.forEach { weatherTermsGlossaryList ->
                weatherTermsGlossaryMutableList.add(WeatherTermsGlossary(term = weatherTermsGlossaryList.term, definition = weatherTermsGlossaryList.definition))
            }
            addAllWeatherTermsGlossary(weatherTermsGlossaryMutableList)
        }
    }

    private fun <T> invokeAPICallsAndPopulateObservables(customWeatherMutableStateFlow: MutableStateFlow<T>, apiWeatherInvocation: suspend() -> Flow<APICallResult<T>>) {
        viewModelScope.launch {
            apiWeatherInvocation().collectLatest { response ->
                when (response) {
                    is APICallResult.Failure -> {
                        isWeatherAPISuccessful = false
                        response.failureResponse?.let { failureResponse ->
                            _failureResponseMutableStateFlow.update { failureResponse }
                        }
                    }

                    is APICallResult.Success -> {
                        isWeatherAPISuccessful = true
                        response.successResponse?.let { weatherResponse ->
                            customWeatherMutableStateFlow.update { weatherResponse }
                        }
                    }
                }
            }
        }
    }
}