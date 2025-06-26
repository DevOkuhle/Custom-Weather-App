package com.example.customweatherapp.ui.navigation

import com.example.customweatherapp.util.Constants.Companion.CHOOSE_CUSTOM_WEATHER_OPTIONS_SCREEN
import com.example.customweatherapp.util.Constants.Companion.CHOOSE_WEATHER_ALERTS_OPTIONS_SCREEN
import com.example.customweatherapp.util.Constants.Companion.CHOOSE_WEATHER_FORECAST_OPTIONS_SCREEN
import com.example.customweatherapp.util.Constants.Companion.SELECTED_AREA_CODE
import com.example.customweatherapp.util.Constants.Companion.SELECTED_REGIONAL_CODE
import com.example.customweatherapp.util.Constants.Companion.VIEW_ALL_WEATHER_ALERT_TYPES_SCREEN
import com.example.customweatherapp.util.Constants.Companion.VIEW_WEATHER_ALERT_BY_AREA_CODE_SCREEN
import com.example.customweatherapp.util.Constants.Companion.VIEW_WEATHER_ALERT_BY_REGIONAL_CODE_SCREEN
import com.example.customweatherapp.util.Constants.Companion.VIEW_WEATHER_API_FAILURE_SCREEN
import com.example.customweatherapp.util.Constants.Companion.VIEW_WEATHER_FORECAST_HOURLY_SCREEN
import com.example.customweatherapp.util.Constants.Companion.VIEW_WEATHER_FORECAST_SCREEN
import com.example.customweatherapp.util.Constants.Companion.VIEW_WEATHER_TERMS_GLOSSARY_SCREEN

sealed class CustomWeatherNavigationScreen(val route: String) {
    data object ChooseCustomWeatherOptionsScreen: CustomWeatherNavigationScreen(route = CHOOSE_CUSTOM_WEATHER_OPTIONS_SCREEN)
    data object ChooseWeatherAlertsOptionsScreen: CustomWeatherNavigationScreen(route = CHOOSE_WEATHER_ALERTS_OPTIONS_SCREEN)
    data object ViewAllWeatherAlertTypesScreen: CustomWeatherNavigationScreen(route = VIEW_ALL_WEATHER_ALERT_TYPES_SCREEN)
    data object ViewWeatherAlertsByAreaCodeScreen: CustomWeatherNavigationScreen(route = "$VIEW_WEATHER_ALERT_BY_AREA_CODE_SCREEN/{$SELECTED_AREA_CODE}") {
        fun passSelectedAreaCode(selectedAreaCode: String): String {
            return this.route.replace(oldValue = SELECTED_AREA_CODE, newValue = selectedAreaCode)
        }
    }
    data object ViewWeatherAlertsByRegionalCodeScreen: CustomWeatherNavigationScreen(route = "$VIEW_WEATHER_ALERT_BY_REGIONAL_CODE_SCREEN/{$SELECTED_REGIONAL_CODE}") {
        fun passSelectedRegionalCode(selectedRegionalCode: String): String {
            return this.route.replace(oldValue = SELECTED_REGIONAL_CODE, newValue = selectedRegionalCode)
        }
    }
    data object ChooseWeatherForecastOptionsScreen: CustomWeatherNavigationScreen(route = CHOOSE_WEATHER_FORECAST_OPTIONS_SCREEN)
    data object ViewWeatherForecastScreen: CustomWeatherNavigationScreen(route = VIEW_WEATHER_FORECAST_SCREEN)
    data object ViewWeatherForecastHourlyScreen: CustomWeatherNavigationScreen(route = VIEW_WEATHER_FORECAST_HOURLY_SCREEN)
    data object ViewWeatherTermsGlossaryScreen: CustomWeatherNavigationScreen(route = VIEW_WEATHER_TERMS_GLOSSARY_SCREEN)
    data object CustomWeatherAPIFailureScreen: CustomWeatherNavigationScreen(route = VIEW_WEATHER_API_FAILURE_SCREEN)
}