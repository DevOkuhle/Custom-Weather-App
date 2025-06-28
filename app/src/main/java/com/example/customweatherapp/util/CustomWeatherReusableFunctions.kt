package com.example.customweatherapp.util

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.customweatherapp.R
import com.example.customweatherapp.data.model.ComposableFunctionAttributes
import com.example.customweatherapp.data.model.weatherAlerts.Feature
import com.example.customweatherapp.ui.navigation.CustomWeatherNavigationScreen
import com.example.customweatherapp.util.Constants.Companion.BULLET_CHARACTER
import com.example.customweatherapp.util.ShareCustomWeatherObjects.failureResponse
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.toSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.customweatherapp.data.api.ParcelableFailureResponse
import com.example.customweatherapp.data.model.CustomWeatherGenericCardAttributes
import com.example.customweatherapp.data.model.forecastByGridPoints.response.Period
import com.example.customweatherapp.data.model.forecastByGridPoints.response.WeatherForecastByGridPointsResponse
import com.example.customweatherapp.util.Constants.Companion.DEGREES_SYMBOL
import com.example.customweatherapp.util.Constants.Companion.DEGREE_CHARACTER
import com.example.customweatherapp.util.Constants.Companion.EAST
import com.example.customweatherapp.util.Constants.Companion.NORTH
import com.example.customweatherapp.util.Constants.Companion.SOUTH
import com.example.customweatherapp.util.Constants.Companion.WEST
import com.example.customweatherapp.util.ShareCustomWeatherObjects.magnitudeConversions
import com.example.customweatherapp.util.ShareCustomWeatherObjects.magnitudeConversionsForHourlyForecast
import com.example.customweatherapp.util.ShareCustomWeatherObjects.previousFailureResponse
import com.example.customweatherapp.util.ShareCustomWeatherObjects.selectedAreaCode
import com.example.customweatherapp.util.ShareCustomWeatherObjects.selectedForecastLocation
import com.example.customweatherapp.util.ShareCustomWeatherObjects.selectedHourlyForecastLocation
import com.example.customweatherapp.util.ShareCustomWeatherObjects.selectedRegionCode
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun PopulateEachAlertResponseCard(modifier: Modifier, alertFeature: Feature) = with(alertFeature) {
    val alertAffectedZonesList = properties.areaDescription?.split(";")
    var affectedZoneInBulletFormat = ""
    alertAffectedZonesList?.forEach { affectedZone ->
        if (affectedZone == alertAffectedZonesList.last()) {
            affectedZoneInBulletFormat += "$BULLET_CHARACTER $affectedZone"
            return@forEach
        }
        affectedZoneInBulletFormat += "$BULLET_CHARACTER $affectedZone\n"
    }

    Card(modifier =
        modifier.fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_8dp))) {
        Column {
            properties.certainty?.let {
                AnnotatedTextForWeatherInformationAttributes(modifier, stringResource(R.string.certainty_subtitle), it)
            }
            properties.severity?.let {
                AnnotatedTextForWeatherInformationAttributes(modifier, stringResource(R.string.severity_subtitle), it)
            }
            properties.urgency?.let {
                AnnotatedTextForWeatherInformationAttributes(modifier, stringResource(R.string.urgency_subtitle), it)
            }
            properties.description?.let {
                AnnotatedTextForWeatherInformationAttributes(modifier, stringResource(R.string.description_subtitle), it)
            }
            properties.instruction?.let {
                AnnotatedTextForWeatherInformationAttributes(modifier, stringResource(R.string.instruction_subtitle), it)
            }
            AnnotatedTextForWeatherInformationAttributes(modifier, stringResource(R.string.zone_affected_subtitle), affectedZoneInBulletFormat)
        }
    }
}

@Composable
fun PopulateWeatherForecastResponseCard(modifier: Modifier, weatherForecastByGridPointsResponse: WeatherForecastByGridPointsResponse, innerPadding: PaddingValues, selectedCity: String, isForecastHourly: Boolean = true) = with(weatherForecastByGridPointsResponse) {
    var selectedWeatherPeriodIndex by rememberSaveable { mutableIntStateOf(0) }
    val period = properties.periods[selectedWeatherPeriodIndex]
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = modifier.padding(
                top = dimensionResource(R.dimen.padding_12dp),
                bottom = dimensionResource(R.dimen.padding_12dp),
                start = dimensionResource(R.dimen.padding_8dp),
                end = dimensionResource(R.dimen.padding_8dp)
            ),
            text = stringResource(if (isForecastHourly) R.string.hourly_weather_forecast_response_title else R.string.hourly_weather_forecast_response_title, selectedCity),
            style = MaterialTheme.typography.titleLarge
        )
        PopulatePeriodInstanceHeader(modifier, period)
        Row(
            modifier = modifier.horizontalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_8dp))

        ) {
            properties.periods.forEachIndexed { index, period ->
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    SetTimeForWeatherCard(modifier, period)
                    Card(
                        modifier = modifier.width(if (period.name.isEmpty()) dimensionResource(R.dimen.padding_100dp) else dimensionResource(R.dimen.padding_150dp))
                            .clickable { selectedWeatherPeriodIndex = index },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedWeatherPeriodIndex == index)
                                MaterialTheme.colorScheme.primaryContainer
                            else
                                MaterialTheme.colorScheme.surface
                        )
                    ) {
                        SetCardAttributes(modifier, period)
                    }
                }
            }
        }
        Box(
            modifier = modifier.fillMaxWidth()
                .height(dimensionResource(R.dimen.padding_2dp))
                .background(if (isSystemInDarkTheme()) Color.LightGray else Color.Black)
                .padding(
                    top = dimensionResource(R.dimen.padding_16dp),
                    bottom = dimensionResource(R.dimen.padding_16dp)
                )
        )
        PopulateRemainingWeatherPeriodAttributes(modifier, period)
    }
}

@Composable
fun PopulateRemainingWeatherPeriodAttributes(modifier: Modifier, period: Period) {
    val formattedDateToEnglish = formatDate(period.startTime.split("T").first())
    val nameOfDay = if (period.name.isNotEmpty()) "${period.name}, $formattedDateToEnglish" else formattedDateToEnglish
    Column {
        Text(
            modifier = modifier.padding(
                top = dimensionResource(R.dimen.padding_12dp),
                bottom = dimensionResource(R.dimen.padding_12dp),
                start = dimensionResource(R.dimen.padding_8dp),
                end = dimensionResource(R.dimen.padding_8dp)
            ),
            text = nameOfDay,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = Bold
        )
        Text(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_8dp)),
            text = stringResource(R.string.wind_speed, period.windSpeed),
            style = MaterialTheme.typography.bodyMedium
        )
        if (period.windDirection.isNotEmpty()) {
            Text(
                modifier = modifier.padding(dimensionResource(R.dimen.padding_8dp)),
                text = stringResource(R.string.wind_direction, mapWindDirection(period.windDirection)),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (period.dewPoint.unitCode.isNotEmpty()) {
            Text(
                modifier = modifier.padding(dimensionResource(R.dimen.padding_8dp)),
                text = stringResource(R.string.dew_point, period.dewPoint.value.roundToInt().toString() + determineTemperatureUnit(period.dewPoint.unitCode.split(":").last().trim())),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_8dp)),
            text = stringResource(R.string.precipitation, period.probabilityOfPrecipitation.value.toString()),
            style = MaterialTheme.typography.bodyMedium
        )
        if (period.detailedForecast.isNotEmpty()) {
            Text(
                modifier = modifier.padding(
                    top = dimensionResource(R.dimen.padding_12dp),
                    bottom = dimensionResource(R.dimen.padding_12dp),
                    start = dimensionResource(R.dimen.padding_8dp),
                    end = dimensionResource(R.dimen.padding_8dp)
                ),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = MaterialTheme.typography.titleMedium.fontSize, fontWeight = Bold)
                    ) {
                        append(stringResource(R.string.detailed_forecast))
                    }
                    withStyle(style = SpanStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize)) {
                        append(period.detailedForecast)
                    }
                }
            )
        }
    }
}

private fun determineTemperatureUnit(temperatureUnit: String): String {
    return if (temperatureUnit == DEGREES_SYMBOL) {
        "${DEGREE_CHARACTER}C"
    } else {
        "${DEGREE_CHARACTER}F"
    }
}

private fun formatDate(inputDate: String): String {
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val outputFormatter = SimpleDateFormat("d MMMM yyyy", Locale.US)

    val date = inputFormatter.parse(inputDate) ?: return ""
    return outputFormatter.format(date)
}

@Composable
fun PopulatePeriodInstanceHeader(modifier: Modifier, period: Period) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_8dp)),
            verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_8dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(period.icon)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = buildAnnotatedString {
                append(period.temperature.toString())
                withStyle(SpanStyle(fontSize = 10.sp, baselineShift = BaselineShift.Superscript)
                ) {
                    append(DEGREE_CHARACTER.toString() + period.temperatureUnit)
                }
            },
            fontSize = 20.sp
        )

        Text(
            modifier = modifier.fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_8dp))
                .wrapContentWidth(align = Alignment.CenterHorizontally),
            text = period.shortForecast,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun SetTimeForWeatherCard(modifier: Modifier, period: Period) {
    val startDateAndTime = period.startTime.split("T")
    val endDateAndTime = period.endTime.split("T")
    Text(
        modifier = modifier.fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .padding(dimensionResource(R.dimen.padding_8dp)),
        text = "${startDateAndTime.last().split("-").first().trim().substring(0,5)}-${endDateAndTime.last().split("-").first().trim().substring(0,5)}",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun SetCardAttributes(modifier: Modifier,period: Period) {
    if (period.name.isNotEmpty()) {
        Text(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_8dp)),
            text = period.name,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    AsyncImage(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_8dp)),
        model = ImageRequest.Builder(LocalContext.current)
            .data(period.icon.split("=").first() + stringResource(R.string.large_picture))
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    Text(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_8dp)),
        text = period.temperature.toString() + DEGREE_CHARACTER + period.temperatureUnit.trim(),
        style = MaterialTheme.typography.titleSmall
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetUpScaffoldTopBar(composableFunctionAttributes: ComposableFunctionAttributes, isAbleToNavigateBack: Boolean = true) = with(composableFunctionAttributes){
    TopAppBar(
        modifier = modifier.height(dimensionResource(R.dimen.padding_65dp))
            .fillMaxWidth(),
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically)
                    .padding(top = dimensionResource(R.dimen.padding_4dp)),
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (isAbleToNavigateBack) {
                IconButton(onClick = { navigationController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun DisplayCircularProgressIndicator(paddingValues: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
    ) {
        Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_16dp)))

        CircularProgressIndicator(
            modifier = Modifier.size(dimensionResource(R.dimen.padding_80dp)),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun AnnotatedTextForWeatherInformationAttributes(modifier: Modifier,labelText: String, descriptionText: String) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = MaterialTheme.typography.titleLarge.fontSize, fontWeight = FontWeight.Bold)) {
                append(labelText)
            }
            withStyle(style = SpanStyle(fontSize = MaterialTheme.typography.bodyLarge.fontSize)) {
                append(descriptionText)
            }
        },
        modifier = modifier.padding(dimensionResource(R.dimen.padding_12dp))
    )
}

@Composable
fun HandleFailureEvents(composableFunctionAttributes: ComposableFunctionAttributes) {
    composableFunctionAttributes.navigationController.currentBackStackEntry?.savedStateHandle?.set(stringResource(R.string.failure_response_parcelable), failureResponse)
    composableFunctionAttributes.navigationController.navigate(CustomWeatherNavigationScreen.CustomWeatherAPIFailureScreen.route)
}

@Composable
fun PopulateCardForAlertOrForecast(modifier: Modifier, isAlertEvent: Boolean, customWeatherGenericCardAttributes: CustomWeatherGenericCardAttributes, onClickAction: () -> Unit) = with(customWeatherGenericCardAttributes) {
    Card(modifier =
        modifier.fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_8dp))) {
        Column {
            Text(
                modifier = modifier.padding(
                    start = dimensionResource(R.dimen.padding_12dp),
                    top = dimensionResource(R.dimen.padding_8dp),
                    bottom = dimensionResource(R.dimen.padding_8dp),
                    end = dimensionResource(R.dimen.padding_8dp)
                ),
                text = evaluateIfEventIsAlertOrForecastThenReturnValue(isAlertEvent, alertAreaOrRegionalCodeTitle, generalOrHourlyForecastTitle),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                modifier = modifier.padding(
                    start = dimensionResource(R.dimen.padding_12dp),
                    top = dimensionResource(R.dimen.padding_8dp),
                    bottom = dimensionResource(R.dimen.padding_8dp),
                    end = dimensionResource(R.dimen.padding_8dp)
                ),
                text = evaluateIfEventIsAlertOrForecastThenReturnValue(isAlertEvent, alertAreaOrRegionalCodeDescription, generalOrHourlyForecastDescription),
                style = MaterialTheme.typography.bodyLarge
            )

            if (!(isAreaCodes == null && isAlertEvent)) {
                if (customWeatherGenericCardAttributes.metricUnitsList.isNotEmpty()) {
                    AlertOrForecastDropdown(modifier, customWeatherGenericCardAttributes.metricUnitsList, customWeatherGenericCardAttributes, isAlertEvent)
                }
                AlertOrForecastDropdown(modifier, areCodeOrRegionList.ifEmpty { generalOrHourlyForecastList }, customWeatherGenericCardAttributes, isAlertEvent)
            }

            Button(
                modifier = modifier.padding(dimensionResource(R.dimen.padding_12dp)),
                onClick = { onClickAction() }
            ) {
                Text(
                    text = evaluateIfEventIsAlertOrForecastThenReturnValue(isAlertEvent, alertButtonLabel, forecastButtonLabel),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

private fun evaluateIfEventIsAlertOrForecastThenReturnValue(isAlertOrForecastEvent: Boolean, alertAssociatedValue: String, forecastAssociatedValue: String): String {
    return if (isAlertOrForecastEvent) {
        alertAssociatedValue
    } else {
        forecastAssociatedValue
    }
}

@Composable
fun AlertOrForecastDropdown(modifier: Modifier, alertCodeOrForecastList: List<String>, customWeatherGenericCardAttributes: CustomWeatherGenericCardAttributes, isAlertEvent: Boolean) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedItemInList by remember { mutableStateOf(alertCodeOrForecastList.first()) }
    var outLinedTextResizableSize by remember { mutableStateOf(Size.Zero) }
    val dropDownIcon = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    storeSelectedItemListForAlertOrForecastEvents(isAlertEvent, customWeatherGenericCardAttributes, selectedItemInList, alertCodeOrForecastList)

    val dropdownViewLabel = getDropdownViewLabel(isAlertEvent, customWeatherGenericCardAttributes.isAreaCodes ?: false, alertCodeOrForecastList)

    Column(modifier.padding(start = dimensionResource(R.dimen.padding_12dp), top = dimensionResource(R.dimen.padding_8dp), bottom = dimensionResource(R.dimen.padding_8dp), end = dimensionResource(R.dimen.padding_8dp))) {
        OutlinedTextField(
            value = if (selectedItemInList.contains(";")) selectedItemInList.split(";").first().trim() else selectedItemInList,
            onValueChange = { selectedItemInList = it },
            modifier =
                modifier.fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        outLinedTextResizableSize = coordinates.size.toSize()
                    },
            label = {Text(dropdownViewLabel)},
            trailingIcon = {
                Icon(
                    imageVector = dropDownIcon,
                    contentDescription = stringResource(R.string.dropdown_description),
                    modifier = modifier.clickable { isExpanded = !isExpanded }
                )
            },
            readOnly = true
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = modifier.width(with(LocalDensity.current) { outLinedTextResizableSize.width.toDp() }),
        ) {
            alertCodeOrForecastList.forEach { label ->
                Text(
                    modifier = modifier.fillMaxWidth()
                        .height(dimensionResource(R.dimen.padding_50dp))
                        .border(
                            width = dimensionResource(R.dimen.padding_1dp),
                            color = MaterialTheme.colorScheme.surface
                        )
                        .padding(
                            top = dimensionResource(R.dimen.padding_14dp),
                            bottom = dimensionResource(R.dimen.padding_14dp),
                            start = dimensionResource(R.dimen.padding_12dp),
                            end = dimensionResource(R.dimen.padding_12dp)
                        )
                        .clickable {
                            selectedItemInList = label
                            storeSelectedItemListForAlertOrForecastEvents(isAlertEvent, customWeatherGenericCardAttributes, selectedItemInList, alertCodeOrForecastList)
                            isExpanded = false
                        },
                    text = if (label.contains(";")) label.split(";").first().trim() else label,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

private fun storeSelectedItemListForAlertOrForecastEvents(isAlertEvent: Boolean, customWeatherGenericCardAttributes: CustomWeatherGenericCardAttributes, selectedItem: String, alertCodeOrForecastList: List<String>) = with(customWeatherGenericCardAttributes) {
    when {
        isAlertEvent && isAreaCodes == true -> {
            selectedAreaCode = selectedItem
        }
        isAlertEvent && isAreaCodes == false -> {
            selectedRegionCode = selectedItem
        }
        !isAlertEvent && alertCodeOrForecastList.size == 2 -> {
            if (isForecastHourly) {
                magnitudeConversionsForHourlyForecast = selectedItem.split("|").first().trim()
            } else {
                magnitudeConversions = selectedItem.split("|").first().trim()
            }
        }
        !isAlertEvent && !isForecastHourly -> {
            selectedForecastLocation = selectedItem
        }
        else -> {
            selectedHourlyForecastLocation = selectedItem
        }
    }
}

@Composable
private fun getDropdownViewLabel(isAlertEvent: Boolean, isAreaCodes: Boolean, alertCodeOrForecastList: List<String>): String {
    return when {
        isAlertEvent -> evaluateIfEventIsAlertOrForecastThenReturnValue(isAreaCodes, stringResource(R.string.area_codes), stringResource(R.string.regional_codes))
        alertCodeOrForecastList.size > 2 -> stringResource(R.string.forecast_office_and_area)
        else -> stringResource(R.string.metric_units)
    }
}

fun mapWindDirection(abbreviatedWindDirection: String): String {
    var windDirection = ""
    abbreviatedWindDirection.forEach { char ->
        windDirection += when(char) {
            'N' -> NORTH
            'S' -> SOUTH
            'E' -> EAST
            else -> WEST
        }
    }
    return windDirection.trim()
}

fun evaluateIfFailureResponseMutableStateFlowIsNotCached(previousSelectedValue: String, newlySelectedValue: String, newFailureResponse: ParcelableFailureResponse): Boolean {
    return previousFailureResponse == null || (previousFailureResponse == newFailureResponse && previousSelectedValue == newlySelectedValue) || (previousFailureResponse != newFailureResponse && previousSelectedValue != newlySelectedValue)
}

fun isFailureResponseMutableStateFlowNotCached(failureResponse: ParcelableFailureResponse, previousFailureForGlossaryTerms: ParcelableFailureResponse?): Boolean {
    return previousFailureResponse != failureResponse && previousFailureResponse == failureResponse && previousFailureForGlossaryTerms == previousFailureResponse
}