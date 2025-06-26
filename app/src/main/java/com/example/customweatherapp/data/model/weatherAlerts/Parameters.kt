package com.example.customweatherapp.data.model.weatherAlerts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Parameters(
    @SerialName("AWIPSidentifier")
    val advancedWeatherInteractiveProcessingSystemIdList: List<String>,
    @SerialName("WMOidentifier")
    val worldMeteorologicalOrganizationIdList: List<String> = emptyList(),
    @SerialName("NWSheadline")
    val nationalWeatherServiceHeadlineList: List<String> = emptyList(),
    @SerialName("BLOCKCHANNEL")
    val blockChannel: List<String> = emptyList(),
    @SerialName("VTEC")
    val validTimeEventCodeList: List<String> = emptyList(),
    val eventEndingTime: List<String> = emptyList(),
    val expiredReferences: List<String> = emptyList()
)