package com.example.customweatherapp.ui

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.customweatherapp.data.model.ComposableFunctionAttributes
import com.example.customweatherapp.util.DisplayCircularProgressIndicator
import com.example.customweatherapp.util.SetUpScaffoldTopBar
import com.example.customweatherapp.util.ShareCustomWeatherObjects.weatherAlertTypes
import com.example.customweatherapp.viewModel.CustomWeatherViewModel
import androidx.core.net.toUri
import com.example.customweatherapp.util.ShareCustomWeatherObjects.failureResponse
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousFailureForAlertsTypes
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousFailureResponse
import com.example.customweatherapp.util.isFailureResponseMutableStateFlowNotCached

@Composable
fun ViewAllWeatherAlertTypes(composableFunctionAttributes: ComposableFunctionAttributes, customWeatherViewModel: CustomWeatherViewModel) = with(composableFunctionAttributes) {
    var loading by remember { mutableStateOf(true) }
    val appContext = LocalContext.current

    Scaffold(modifier = modifier.fillMaxWidth(),
        topBar = {
            SetUpScaffoldTopBar(composableFunctionAttributes)
        }
    ) { innerPadding ->
        if (loading) {
            DisplayCircularProgressIndicator(innerPadding)
            customWeatherViewModel.viewAlertTypesButtonClicked()
            weatherAlertTypes = customWeatherViewModel.allAlertTypesMutableStateFlow.collectAsState().value
            if (weatherAlertTypes.eventTypes.isNotEmpty() && isFailureResponseMutableStateFlowNotCached(failureResponse, previousFailureForAlertsTypes)) {
                previousFailureResponse = failureResponse
                previousFailureForAlertsTypes = failureResponse
                customWeatherViewModel.addAlertTypesToDatabase(weatherAlertTypes.eventTypes)
                loading = false
            }
        } else {
            Column(
                modifier = modifier.fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn {
                    items(weatherAlertTypes.eventTypes) { eventType ->
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        textDecoration = TextDecoration.Underline,
                                        fontSize = 18.sp
                                    )
                                ) {
                                    append(eventType)
                                }
                            },
                            modifier = modifier.clickable {
                                val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                                    putExtra(SearchManager.QUERY, eventType)
                                }

                                if (intent.resolveActivity(appContext.packageManager) != null) {
                                    appContext.startActivity(intent)
                                } else {
                                    val fallbackIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        "https://www.google.com/search?q=${Uri.encode(eventType)}".toUri()
                                    )
                                    appContext.startActivity(fallbackIntent)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}