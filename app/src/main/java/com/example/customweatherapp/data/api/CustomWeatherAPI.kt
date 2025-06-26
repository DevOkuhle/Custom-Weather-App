package com.example.customweatherapp.data.api

import WeatherAlertsResponse
import com.example.customweatherapp.data.model.allAlertTypes.AllAlertTypesResponse
import com.example.customweatherapp.data.model.forecastByGridPoints.response.WeatherForecastByGridPointsResponse
import com.example.customweatherapp.data.model.weatherTermsGlossary.WeatherTermsGlossaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CustomWeatherAPI {

    @GET("/alerts/types")
    suspend fun getAllAlertTypes(): Response<AllAlertTypesResponse>

    @GET("/glossary")
    suspend fun getWeatherTermsGlossary(): Response<WeatherTermsGlossaryResponse>

    @GET("/alerts/active/area/{area}")
    suspend fun getAlertByAreaCode(@Path("area") areaCode: String): Response<WeatherAlertsResponse>

    @GET("/alerts/active/region/{region}")
    suspend fun getAlertByRegion(@Path("region") region: String): Response<WeatherAlertsResponse>

    @GET("/gridpoints/{weatherForecastOfficeID}/{longitudeCoordinate},{latitudeCoordinate}/forecast")
    suspend fun getWeatherForecastByGridPoints(
        @Path("weatherForecastOfficeID") weatherForecastOfficeID: String,
        @Path("longitudeCoordinate") longitudeCoordinate: Int,
        @Path("latitudeCoordinate") latitudeCoordinate: Int,
        @Query("units") unitOfMeasurements: String
    ): Response<WeatherForecastByGridPointsResponse>

    @GET("/gridpoints/{weatherForecastOfficeID}/{longitudeCoordinate},{latitudeCoordinate}/forecast/hourly")
    suspend fun getWeatherForecastByGridPointsHourly(
        @Path("weatherForecastOfficeID") weatherForecastOfficeID: String,
        @Path("longitudeCoordinate") longitudeCoordinate: Int,
        @Path("latitudeCoordinate") latitudeCoordinate: Int,
        @Query("units") unitOfMeasurements: String
    ): Response<WeatherForecastByGridPointsResponse>
}