package com.example.customweatherapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.customweatherapp.data.model.ComposableFunctionAttributes
import com.example.customweatherapp.util.DisplayCircularProgressIndicator
import com.example.customweatherapp.util.SetUpScaffoldTopBar
import com.example.customweatherapp.util.ShareCustomWeatherObjects.weatherTermsGlossaryList
import com.example.customweatherapp.viewModel.CustomWeatherViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import com.example.customweatherapp.R
import com.example.customweatherapp.util.HandleFailureEvents
import com.example.customweatherapp.util.ShareCustomWeatherObjects.failureResponse
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousFailureForGlossaryTerms
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousFailureResponse
import com.example.customweatherapp.util.isFailureResponseMutableStateFlowNotCached

@Composable
fun ViewWeatherTermsGlossary(composableFunctionAttributes: ComposableFunctionAttributes, customWeatherViewModel: CustomWeatherViewModel) = with(composableFunctionAttributes) {
    var loading by remember { mutableStateOf(true) }

    Scaffold(modifier = modifier.fillMaxWidth(),
        topBar = {
            SetUpScaffoldTopBar(composableFunctionAttributes)
        }
    ) { innerPadding ->
        if (loading) {
            DisplayCircularProgressIndicator(innerPadding)
            customWeatherViewModel.viewWeatherTermsGlossaryButtonClicked()
            weatherTermsGlossaryList = customWeatherViewModel.weatherTermsGlossaryMutableStateFlow.collectAsState().value
            failureResponse = customWeatherViewModel.failureResponseMutableStateFlow.collectAsState().value
            if (weatherTermsGlossaryList.glossary.isNotEmpty()) {
                customWeatherViewModel.addWeatherTermsGlossaryToDatabase(weatherTermsGlossaryList.glossary)
                loading = false
            } else if (failureResponse.detail.isNotEmpty() && isFailureResponseMutableStateFlowNotCached(failureResponse, previousFailureForGlossaryTerms)) {
                previousFailureResponse = failureResponse
                previousFailureForGlossaryTerms = failureResponse
                HandleFailureEvents(composableFunctionAttributes)
            }
        } else {
            Column(modifier = modifier.fillMaxSize()
                .padding(innerPadding)
            ) {
                Box(
                    modifier = modifier.fillMaxWidth()
                        .height(dimensionResource(R.dimen.padding_2dp))
                        .background(if (isSystemInDarkTheme()) Color.LightGray else Color.Black)
                )

                Row(
                    modifier = modifier.fillMaxWidth()
                        .padding(
                            top =  dimensionResource(R.dimen.padding_8dp),
                            bottom = dimensionResource(R.dimen.padding_8dp)
                        )
                ) {
                    Text(
                        modifier = modifier.weight(1f)
                            .padding(start = dimensionResource(R.dimen.padding_8dp)),
                        text = stringResource(R.string.term),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = Bold
                    )
                    Text(
                        modifier = modifier.weight(3f)
                            .padding(start = dimensionResource(R.dimen.padding_2dp)),
                        text = stringResource(R.string.definition),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = Bold
                    )
                }

                Box(
                    modifier = modifier.fillMaxWidth()
                        .height(dimensionResource(R.dimen.padding_2dp))
                        .background(if (isSystemInDarkTheme()) Color.LightGray else Color.Black)
                        .padding(
                            bottom = dimensionResource(R.dimen.padding_8dp)
                        )
                )
                LazyColumn (modifier = modifier.fillMaxWidth()) {
                    items(weatherTermsGlossaryList.glossary) { glossary ->
                        Row(modifier = modifier.fillMaxWidth()) {
                            Text(
                                modifier = modifier.weight(1f)
                                    .padding(
                                        start = dimensionResource(R.dimen.padding_8dp),
                                        top = dimensionResource(R.dimen.padding_8dp),
                                        bottom = dimensionResource(R.dimen.padding_8dp)
                                    ),
                                style = MaterialTheme.typography.bodyMedium,
                                text = glossary.term
                            )
                            Text(
                                modifier = modifier.weight(3f)
                                    .padding(
                                        end = dimensionResource(R.dimen.padding_8dp),
                                        start = dimensionResource(R.dimen.padding_2dp),
                                        top = dimensionResource(R.dimen.padding_8dp),
                                        bottom = dimensionResource(R.dimen.padding_8dp)
                                    ),
                                style = MaterialTheme.typography.bodyMedium,
                                text = glossary.definition
                            )
                        }
                    }
                }
            }
        }
    }
}