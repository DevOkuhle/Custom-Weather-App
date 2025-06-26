package com.example.customweatherapp.data.model

data class CustomWeatherGenericCardAttributes(
    var isAreaCodes: Boolean? = null,
    var alertButtonLabel: String = "",
    var alertAreaOrRegionalCodeTitle: String = "",
    var alertAreaOrRegionalCodeDescription: String = "",
    var areCodeOrRegionList: List<String> = emptyList(),
    var isForecastHourly: Boolean = false,
    var forecastButtonLabel: String = "",
    var generalOrHourlyForecastTitle: String = "",
    var generalOrHourlyForecastDescription: String = "",
    var generalOrHourlyForecastList: List<String> = emptyList(),
    var metricUnitsList: List<String> = emptyList()
)