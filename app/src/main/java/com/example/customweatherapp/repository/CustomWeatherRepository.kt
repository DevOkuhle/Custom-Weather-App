package com.example.customweatherapp.repository

import WeatherAlertsResponse
import com.example.customweatherapp.data.api.APICallResult
import com.example.customweatherapp.data.model.allAlertTypes.AllAlertTypesResponse
import com.example.customweatherapp.data.model.forecastByGridPoints.request.GridPointsForecastRequest
import com.example.customweatherapp.data.model.forecastByGridPoints.response.WeatherForecastByGridPointsResponse
import com.example.customweatherapp.data.model.weatherTermsGlossary.WeatherTermsGlossaryResponse
import com.example.customweatherapp.room.AlertTypes
import com.example.customweatherapp.room.WeatherTermsGlossary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CustomWeatherRepository {

    suspend fun getAllAlertTypes(): Flow<APICallResult<AllAlertTypesResponse>>

    suspend fun getWeatherTermsGlossary(): Flow<APICallResult<WeatherTermsGlossaryResponse>>

    suspend fun getAlertByAreaCode(areaCode: String): Flow<APICallResult<WeatherAlertsResponse>>

    suspend fun getAlertByRegion(region: String): Flow<APICallResult<WeatherAlertsResponse>>

    suspend fun getWeatherForecastByGridPoints(gridPointsForecastRequest: GridPointsForecastRequest): Flow<APICallResult<WeatherForecastByGridPointsResponse>>

    suspend fun getWeatherForecastByGridPointsHourly(gridPointsForecastRequest: GridPointsForecastRequest): Flow<APICallResult<WeatherForecastByGridPointsResponse>>

    suspend fun addAlertTypes(alertTypes: List<AlertTypes>)

    suspend fun readAllAlertTypes(): Flow<List<AlertTypes>>

    suspend fun addWeatherTermsGlossary(weatherTermsGlossary: List<WeatherTermsGlossary>)

    suspend fun readAllWeatherTermsGlossary(): Flow<List<WeatherTermsGlossary>>

    suspend fun isWeatherTermsGlossaryTableNotEmpty(viewModelScope: CoroutineScope): Flow<Boolean>

    suspend fun isAlertTypesTableNotEmpty(viewModelScope: CoroutineScope): Flow<Boolean>
}