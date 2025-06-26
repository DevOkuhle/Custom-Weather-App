package com.example.customweatherapp.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.customweatherapp.data.model.ComposableFunctionAttributes
import com.example.customweatherapp.ui.*
import com.example.customweatherapp.util.Constants.Companion.SELECTED_AREA_CODE
import com.example.customweatherapp.util.Constants.Companion.SELECTED_REGIONAL_CODE
import com.example.customweatherapp.viewModel.CustomWeatherViewModel

@Composable
fun SetupCustomWeatherNavigationGraph(composableFunctionAttributes: ComposableFunctionAttributes, customWeatherViewModel: CustomWeatherViewModel) = with(composableFunctionAttributes) {
    NavHost(navController = navigationController, startDestination = CustomWeatherNavigationScreen.ChooseCustomWeatherOptionsScreen.route) {
        composable(route = CustomWeatherNavigationScreen.ChooseCustomWeatherOptionsScreen.route) {
            ChooseCustomWeatherOptions(composableFunctionAttributes)
        }

        composable(route = CustomWeatherNavigationScreen.ChooseWeatherAlertsOptionsScreen.route) {
            ChooseWeatherAlertsOptions(composableFunctionAttributes)
        }

        composable(route = CustomWeatherNavigationScreen.ViewAllWeatherAlertTypesScreen.route) {
            ViewAllWeatherAlertTypes(composableFunctionAttributes, customWeatherViewModel)
        }

        composable(
            route = CustomWeatherNavigationScreen.ViewWeatherAlertsByAreaCodeScreen.route,
            arguments = listOf(navArgument(SELECTED_AREA_CODE) {
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString(SELECTED_AREA_CODE)?.let { areaCode ->
                composableFunctionAttributes.alertAreaCode = areaCode
                ViewWeatherAlertsByAreaCode(composableFunctionAttributes, customWeatherViewModel)
            }
        }

        composable(
            route = CustomWeatherNavigationScreen.ViewWeatherAlertsByRegionalCodeScreen.route,
            arguments = listOf(navArgument(SELECTED_REGIONAL_CODE) {
                type = NavType.StringType
            })
        ){
            it.arguments?.getString(SELECTED_REGIONAL_CODE)?.let { regionalCode ->
                composableFunctionAttributes.alertRegionalCode = regionalCode
                ViewWeatherAlertsByRegionalCode(composableFunctionAttributes, customWeatherViewModel)
            }
        }

        composable(route = CustomWeatherNavigationScreen.ChooseWeatherForecastOptionsScreen.route) {
            ChooseWeatherForecastOptions(composableFunctionAttributes)
        }

        composable(route = CustomWeatherNavigationScreen.ViewWeatherForecastScreen.route) {
                ViewWeatherForecast(composableFunctionAttributes, customWeatherViewModel)
        }

        composable(route = CustomWeatherNavigationScreen.ViewWeatherForecastHourlyScreen.route) {
            ViewWeatherForecastHourly(composableFunctionAttributes, customWeatherViewModel)
        }

        composable(route = CustomWeatherNavigationScreen.ViewWeatherTermsGlossaryScreen.route) {
            ViewWeatherTermsGlossary(composableFunctionAttributes, customWeatherViewModel)
        }

        composable(route = CustomWeatherNavigationScreen.CustomWeatherAPIFailureScreen.route) {
            BackHandler(false) {}
            CustomWeatherAPIFailure(composableFunctionAttributes)
        }
    }
}