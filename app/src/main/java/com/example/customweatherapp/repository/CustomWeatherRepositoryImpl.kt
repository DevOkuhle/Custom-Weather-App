package com.example.customweatherapp.repository

import WeatherAlertsResponse
import com.example.customweatherapp.data.api.APICallResult
import com.example.customweatherapp.data.api.CustomWeatherAPI
import com.example.customweatherapp.data.model.allAlertTypes.AllAlertTypesResponse
import com.example.customweatherapp.data.model.forecastByGridPoints.request.GridPointsForecastRequest
import com.example.customweatherapp.data.model.forecastByGridPoints.response.WeatherForecastByGridPointsResponse
import com.example.customweatherapp.data.model.weatherTermsGlossary.WeatherTermsGlossaryResponse
import com.example.customweatherapp.room.AlertTypes
import com.example.customweatherapp.room.CustomWeatherDao
import com.example.customweatherapp.room.WeatherTermsGlossary
import com.example.customweatherapp.util.APISetup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomWeatherRepositoryImpl @Inject constructor(private val customWeatherAPI: CustomWeatherAPI, private val customWeatherDao: CustomWeatherDao): CustomWeatherRepository {
    private val apiSetup: APISetup = APISetup()
    override suspend fun getAllAlertTypes(): Flow<APICallResult<AllAlertTypesResponse>> = withContext(Dispatchers.IO) {
        apiSetup.populateResponseFromWeatherAPI { customWeatherAPI.getAllAlertTypes() }
    }

    override suspend fun getWeatherTermsGlossary(): Flow<APICallResult<WeatherTermsGlossaryResponse>>  = withContext(Dispatchers.IO) {
        apiSetup.populateResponseFromWeatherAPI { customWeatherAPI.getWeatherTermsGlossary() }
    }

    override suspend fun getAlertByAreaCode(areaCode: String): Flow<APICallResult<WeatherAlertsResponse>> = withContext(Dispatchers.IO) {
        apiSetup.populateResponseFromWeatherAPI { customWeatherAPI.getAlertByAreaCode(areaCode) }
    }

    override suspend fun getAlertByRegion(region: String): Flow<APICallResult<WeatherAlertsResponse>> = withContext(Dispatchers.IO) {
        apiSetup.populateResponseFromWeatherAPI { customWeatherAPI.getAlertByRegion(region) }
    }

    override suspend fun getWeatherForecastByGridPoints(gridPointsForecastRequest: GridPointsForecastRequest): Flow<APICallResult<WeatherForecastByGridPointsResponse>> =
        withContext(Dispatchers.IO) {
            apiSetup.populateResponseFromWeatherAPI {
                customWeatherAPI.getWeatherForecastByGridPoints(
                    gridPointsForecastRequest.weatherForecastOfficeID,
                    gridPointsForecastRequest.longitudeCoordinate,
                    gridPointsForecastRequest.latitudeCoordinate,
                    gridPointsForecastRequest.unitOfMeasurements
                )
            }
        }

    override suspend fun getWeatherForecastByGridPointsHourly(gridPointsForecastRequest: GridPointsForecastRequest): Flow<APICallResult<WeatherForecastByGridPointsResponse>> =
        withContext(Dispatchers.IO) {
            apiSetup.populateResponseFromWeatherAPI {
                customWeatherAPI.getWeatherForecastByGridPointsHourly(
                    gridPointsForecastRequest.weatherForecastOfficeID,
                    gridPointsForecastRequest.longitudeCoordinate,
                    gridPointsForecastRequest.latitudeCoordinate,
                    gridPointsForecastRequest.unitOfMeasurements
                )
            }
        }

    override suspend fun addAlertTypes(alertTypes: List<AlertTypes>) = withContext(Dispatchers.IO) {
        customWeatherDao.addAlertTypes(alertTypes)
    }

    override suspend fun readAllAlertTypes(): Flow<List<AlertTypes>>  = withContext(Dispatchers.IO) {
        customWeatherDao.readAllAlertTypes()
    }

    override suspend fun addWeatherTermsGlossary(weatherTermsGlossary: List<WeatherTermsGlossary>) = withContext(Dispatchers.IO) {
        customWeatherDao.addWeatherGlossary(weatherTermsGlossary)
    }

    override suspend fun readAllWeatherTermsGlossary(): Flow<List<WeatherTermsGlossary>> = withContext(Dispatchers.IO) {
        customWeatherDao.readAllWeatherTermsGlossary()
    }

    override suspend fun isWeatherTermsGlossaryTableNotEmpty(viewModelScope: CoroutineScope): Flow<Boolean> = withContext(Dispatchers.IO) {
        customWeatherDao.isWeatherTermsGlossaryTableNotEmpty().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    }

    override suspend fun isAlertTypesTableNotEmpty(viewModelScope: CoroutineScope): Flow<Boolean> = withContext(Dispatchers.IO) {
        customWeatherDao.isAlertTypesTableNotEmpty().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    }
}