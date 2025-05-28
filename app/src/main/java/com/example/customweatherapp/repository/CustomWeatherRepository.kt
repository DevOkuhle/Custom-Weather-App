package com.example.customweatherapp.repository

import com.example.customweatherapp.data.api.APICallResult
import com.example.customweatherapp.data.model.allAlertTypes.AllAlertTypesResponse
import com.example.customweatherapp.data.model.forecastByGridPoints.request.GridPointsForecastRequest
import com.example.customweatherapp.data.model.forecastByGridPoints.response.WeatherForecastByGridPointsResponse
import com.example.customweatherapp.data.model.weatherAlerts.WeatherAlertsResponse
import com.example.customweatherapp.data.model.weatherTermsGlossary.WeatherTermsGlossaryResponse
import kotlinx.coroutines.flow.Flow

interface CustomWeatherRepository {

    suspend fun getAllAlertTypes(): Flow<APICallResult<AllAlertTypesResponse>>

    suspend fun getWeatherTermsGlossary(): Flow<APICallResult<WeatherTermsGlossaryResponse>>

    suspend fun getAlertByAreaCode(areaCode: String): Flow<APICallResult<WeatherAlertsResponse>>

    suspend fun getAlertByZoneIdentifier(zoneIdentifier: String): Flow<APICallResult<WeatherAlertsResponse>>

    suspend fun getAlertByRegion(region: String): Flow<APICallResult<WeatherAlertsResponse>>

    suspend fun getWeatherForecastByGridPoints(gridPointsForecastRequest: GridPointsForecastRequest): Flow<APICallResult<WeatherForecastByGridPointsResponse>>

    suspend fun getWeatherForecastByGridPointsHourly(gridPointsForecastRequest: GridPointsForecastRequest): Flow<APICallResult<WeatherForecastByGridPointsResponse>>
}