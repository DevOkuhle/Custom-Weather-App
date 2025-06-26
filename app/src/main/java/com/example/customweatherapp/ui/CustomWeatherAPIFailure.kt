package com.example.customweatherapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.customweatherapp.R
import com.example.customweatherapp.data.api.ParcelableFailureResponse
import com.example.customweatherapp.data.model.ComposableFunctionAttributes
import com.example.customweatherapp.ui.navigation.CustomWeatherNavigationScreen
import com.example.customweatherapp.util.SetUpScaffoldTopBar
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousFailureResponse

@Composable
fun CustomWeatherAPIFailure(composableFunctionAttributes: ComposableFunctionAttributes) = with(composableFunctionAttributes) {
    val failureResponse = navigationController.previousBackStackEntry?.savedStateHandle?.get<ParcelableFailureResponse>(
        stringResource(R.string.failure_response_parcelable)
    )

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            SetUpScaffoldTopBar(composableFunctionAttributes, false)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Icon(
                modifier = modifier.padding(dimensionResource(R.dimen.padding_30dp)),
                painter = painterResource(R.drawable.error_icon),
                contentDescription = stringResource(id = R.string.error_icon_description)
            )

            failureResponse?.let { failureResponse ->
                Text(
                    modifier = modifier.fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .padding(dimensionResource(R.dimen.padding_12dp)),
                    text = failureResponse.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    modifier = modifier.fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_12dp)),
                    text = failureResponse.detail,
                    style = MaterialTheme.typography.titleLarge
                )
                previousFailureResponse = failureResponse
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_15dp))
            ) {
                Button(
                    onClick = { navigationController.navigate(route = CustomWeatherNavigationScreen.ChooseCustomWeatherOptionsScreen.route) },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.home_screen),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}