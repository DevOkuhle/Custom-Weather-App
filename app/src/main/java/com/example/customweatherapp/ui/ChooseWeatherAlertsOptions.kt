package com.example.customweatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.example.customweatherapp.R
import com.example.customweatherapp.data.model.ComposableFunctionAttributes
import com.example.customweatherapp.data.model.CustomWeatherGenericCardAttributes
import com.example.customweatherapp.ui.navigation.CustomWeatherNavigationScreen
import com.example.customweatherapp.util.PopulateCardForAlertOrForecast
import com.example.customweatherapp.util.SetUpScaffoldTopBar
import com.example.customweatherapp.util.ShareCustomWeatherObjects

@Composable
fun ChooseWeatherAlertsOptions(composableFunctionAttributes: ComposableFunctionAttributes) = with(composableFunctionAttributes) {
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            SetUpScaffoldTopBar(composableFunctionAttributes)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            PopulateCardForAlertOrForecast(
                modifier,
                true,
                CustomWeatherGenericCardAttributes(
                    isAreaCodes = true,
                    alertButtonLabel = stringResource(R.string.view_area_weather_alerts),
                    areCodeOrRegionList = stringArrayResource(R.array.areaCodes).toList(),
                    alertAreaOrRegionalCodeTitle = stringResource(R.string.weather_alerts_by_area_code),
                    alertAreaOrRegionalCodeDescription = stringResource(R.string.view_weather_alerts_by_area_code_instruction)
                )
            ) {
                navigationController.navigate(
                    route = CustomWeatherNavigationScreen.ViewWeatherAlertsByAreaCodeScreen.passSelectedAreaCode(ShareCustomWeatherObjects.selectedAreaCode)
                )
            }
            PopulateCardForAlertOrForecast(
                modifier,
                true,
                CustomWeatherGenericCardAttributes(
                    isAreaCodes = false,
                    alertButtonLabel = stringResource(R.string.view_regional_weather_alerts),
                    areCodeOrRegionList = stringArrayResource(R.array.regionalCodes).toList(),
                    alertAreaOrRegionalCodeTitle = stringResource(R.string.weather_alerts_by_regional_code),
                    alertAreaOrRegionalCodeDescription = stringResource(R.string.view_weather_alerts_by_regional_code_instruction)
                )
            ) {
                navigationController.navigate(
                    route = CustomWeatherNavigationScreen.ViewWeatherAlertsByRegionalCodeScreen.passSelectedRegionalCode(ShareCustomWeatherObjects.selectedRegionCode)
                )
            }
            PopulateCardForAlertOrForecast(
                modifier,
                true,
                CustomWeatherGenericCardAttributes(
                    alertButtonLabel = stringResource(R.string.view_weather_alerts_types),
                    alertAreaOrRegionalCodeTitle = stringResource(R.string.weather_alerts_types),
                    alertAreaOrRegionalCodeDescription = stringResource(R.string.view_weather_alerts_types_instruction)
                )
            ) {
                navigationController.navigate(route = CustomWeatherNavigationScreen.ViewAllWeatherAlertTypesScreen.route)
            }
        }
    }
}