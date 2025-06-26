package com.example.customweatherapp.util

import WeatherAlertsResponse
import com.example.customweatherapp.data.api.ParcelableFailureResponse
import com.example.customweatherapp.data.model.allAlertTypes.AllAlertTypesResponse
import com.example.customweatherapp.data.model.forecastByGridPoints.response.WeatherForecastByGridPointsResponse
import com.example.customweatherapp.data.model.weatherTermsGlossary.WeatherTermsGlossaryResponse

object ShareCustomWeatherObjects {
    var selectedAreaCode: String = ""
    var selectedRegionCode: String = ""
    var selectedForecastLocation: String = ""
    var selectedHourlyForecastLocation: String = ""
    var magnitudeConversions: String = ""
    var magnitudeConversionsForHourlyForecast: String = ""
    var failureResponse: ParcelableFailureResponse = ParcelableFailureResponse()
    var previousAreaCode: String = ""
    var previousAreaCodeWeatherAlertsResponse: WeatherAlertsResponse? = null
    var previousForecastInformation: String = ""
    var previousWeatherForecastByGridPointsResponse: WeatherForecastByGridPointsResponse? = null
    var previousFailureResponse: ParcelableFailureResponse? = null
    var previousHourlyForecastInformation: String = ""
    var previousHourlyWeatherForecastByGridPointsResponse: WeatherForecastByGridPointsResponse? = null
    var previousRegionalCode: String = ""
    var previousFailureForGlossaryTerms: ParcelableFailureResponse? = null
    var previousFailureForAlertsTypes: ParcelableFailureResponse? = null
    var previousRegionalCodeWeatherAlertsResponse: WeatherAlertsResponse? = null
    var weatherTermsGlossaryList: WeatherTermsGlossaryResponse = WeatherTermsGlossaryResponse()
    var weatherAlertTypes: AllAlertTypesResponse = AllAlertTypesResponse()
}