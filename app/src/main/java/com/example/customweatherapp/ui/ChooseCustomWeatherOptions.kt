package com.example.customweatherapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.customweatherapp.R
import com.example.customweatherapp.data.model.ComposableFunctionAttributes
import com.example.customweatherapp.data.model.CustomWeatherCardAttributes
import com.example.customweatherapp.ui.navigation.CustomWeatherNavigationScreen
import com.example.customweatherapp.util.Constants.Companion.HEIGHT_SCREEN_QUARTER_DIVISOR
import com.example.customweatherapp.util.SetUpScaffoldTopBar
import kotlin.math.floor

@Composable
fun ChooseCustomWeatherOptions(composableFunctionAttributes: ComposableFunctionAttributes) = with(composableFunctionAttributes) {
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            SetUpScaffoldTopBar(composableFunctionAttributes, false)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            PopulateEachCard(
                modifier = modifier,
                customWeatherCardAttributes = CustomWeatherCardAttributes(
                    imageResource = R.drawable.weather_alerts_image,
                    imageDescription = R.string.weather_alert_image_description,
                    titleText = R.string.weather_alert_title,
                    descriptionText = R.string.weather_alert_description,
                    buttonText = R.string.view_weather_alerts
                )
            ) {
                navigationController.navigate(route = CustomWeatherNavigationScreen.ChooseWeatherAlertsOptionsScreen.route)
            }

            PopulateEachCard(
                modifier = modifier,
                customWeatherCardAttributes = CustomWeatherCardAttributes(
                    imageResource = R.drawable.weather_forecast,
                    imageDescription = R.string.weather_forecast_image_description,
                    titleText = R.string.weather_forecast_title,
                    descriptionText = R.string.weather_forecast_description,
                    buttonText = R.string.view_weather_forecast
                )
            ) {
                navigationController.navigate(route = CustomWeatherNavigationScreen.ChooseWeatherForecastOptionsScreen.route)
            }

            PopulateEachCard(
                modifier = modifier,
                customWeatherCardAttributes = CustomWeatherCardAttributes(
                    imageResource = R.drawable.weather_terms_glossary,
                    imageDescription = R.string.weather_terms_glossary_image_description,
                    titleText = R.string.weather_terms_glossary_title,
                    descriptionText = R.string.weather_terms_glossary_description,
                    buttonText = R.string.view_weather_terms_glossary
                )
            ) {
                navigationController.navigate(route = CustomWeatherNavigationScreen.ViewWeatherTermsGlossaryScreen.route)
            }
        }
    }
}

@Composable
fun PopulateEachCard(modifier: Modifier, customWeatherCardAttributes: CustomWeatherCardAttributes, onClickAction: () -> Unit) {
    Card(modifier = modifier.padding(dimensionResource(R.dimen.padding_8dp))) {
        Column {
            Image(
                modifier = modifier.fillMaxWidth()
                    .height(floor(LocalConfiguration.current.screenHeightDp.toFloat() / HEIGHT_SCREEN_QUARTER_DIVISOR).dp),
                painter = painterResource(id = customWeatherCardAttributes.imageResource),
                contentDescription = stringResource(id = customWeatherCardAttributes.imageDescription),
                contentScale = ContentScale.FillWidth
            )

            Text(
                modifier = modifier.padding(
                    start = dimensionResource(R.dimen.padding_12dp),
                    top = dimensionResource(R.dimen.padding_8dp),
                    bottom = dimensionResource(R.dimen.padding_8dp),
                    end = dimensionResource(R.dimen.padding_12dp)
                ),
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(customWeatherCardAttributes.titleText)
            )

            Text(
                modifier = modifier.padding(
                    start = dimensionResource(R.dimen.padding_12dp),
                    top = dimensionResource(R.dimen.padding_8dp),
                    bottom = dimensionResource(R.dimen.padding_8dp),
                    end = dimensionResource(R.dimen.padding_8dp)
                ),
                style = MaterialTheme.typography.bodySmall,
                text = stringResource(customWeatherCardAttributes.descriptionText)
            )

            Button(
                modifier = modifier.padding(dimensionResource(R.dimen.padding_12dp)),
                onClick = { onClickAction() }
            ) {
                Text(
                    text = stringResource(customWeatherCardAttributes.buttonText),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}