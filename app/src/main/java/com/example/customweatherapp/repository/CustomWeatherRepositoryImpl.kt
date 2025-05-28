package com.example.customweatherapp.repository

import com.example.customweatherapp.data.api.APICallResult
import com.example.customweatherapp.data.api.CustomWeatherAPI
import com.example.customweatherapp.data.model.allAlertTypes.AllAlertTypesResponse
import com.example.customweatherapp.data.model.forecastByGridPoints.request.GridPointsForecastRequest
import com.example.customweatherapp.data.model.forecastByGridPoints.response.WeatherForecastByGridPointsResponse
import com.example.customweatherapp.data.model.weatherAlerts.WeatherAlertsResponse
import com.example.customweatherapp.data.model.weatherTermsGlossary.WeatherTermsGlossaryResponse
import com.example.customweatherapp.util.APISetup
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomWeatherRepositoryImpl @Inject constructor(private val customWeatherAPI: CustomWeatherAPI): CustomWeatherRepository {
    private val apiSetup: APISetup = APISetup()
    override suspend fun getAllAlertTypes(): Flow<APICallResult<AllAlertTypesResponse>> =
        apiSetup.populateResponseFromWeatherAPI { customWeatherAPI.getAllAlertTypes() }

    override suspend fun getWeatherTermsGlossary(): Flow<APICallResult<WeatherTermsGlossaryResponse>>  =
        apiSetup.populateResponseFromWeatherAPI { customWeatherAPI.getWeatherTermsGlossary() }

    override suspend fun getAlertByAreaCode(areaCode: String): Flow<APICallResult<WeatherAlertsResponse>> =
        apiSetup.populateResponseFromWeatherAPI { customWeatherAPI.getAlertByAreaCode(areaCode) }

    override suspend fun getAlertByZoneIdentifier(zoneIdentifier: String): Flow<APICallResult<WeatherAlertsResponse>> =
        apiSetup.populateResponseFromWeatherAPI { customWeatherAPI.getAlertByZoneIdentifier(zoneIdentifier) }

    override suspend fun getAlertByRegion(region: String): Flow<APICallResult<WeatherAlertsResponse>> =
        apiSetup.populateResponseFromWeatherAPI { customWeatherAPI.getAlertByRegion(region) }

    override suspend fun getWeatherForecastByGridPoints(gridPointsForecastRequest: GridPointsForecastRequest): Flow<APICallResult<WeatherForecastByGridPointsResponse>> =
        apiSetup.populateResponseFromWeatherAPI {
            customWeatherAPI.getWeatherForecastByGridPoints(
                gridPointsForecastRequest.weatherForecastOfficeID,
                gridPointsForecastRequest.latitudeCoordinate,
                gridPointsForecastRequest.longitudeCoordinate,
                gridPointsForecastRequest.unitOfMeasurements
            )
        }

    override suspend fun getWeatherForecastByGridPointsHourly(gridPointsForecastRequest: GridPointsForecastRequest): Flow<APICallResult<WeatherForecastByGridPointsResponse>> =
        apiSetup.populateResponseFromWeatherAPI {
            customWeatherAPI.getWeatherForecastByGridPointsHourly(
                gridPointsForecastRequest.weatherForecastOfficeID,
                gridPointsForecastRequest.latitudeCoordinate,
                gridPointsForecastRequest.longitudeCoordinate,
                gridPointsForecastRequest.unitOfMeasurements
            )
        }
}