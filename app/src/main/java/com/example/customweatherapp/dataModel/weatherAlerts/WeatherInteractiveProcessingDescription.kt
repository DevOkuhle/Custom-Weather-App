package com.example.customweatherapp.dataModel.weatherAlerts

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherInteractiveProcessingDescription(
    @JsonProperty("AWIPSidentifier")
    var advancedWeatherInteractiveProcessingSystemIdList: List<String> = emptyList(),
    @JsonProperty("BLOCKCHANNEL")
    var blockChannelList: List<String> = emptyList(),
    @JsonProperty("NWSheadline")
    var nationalWeatherServiceHeadlineList: List<String> = emptyList(),
    @JsonProperty("VTEC")
    var validTimeEventCodeList: List<String> = emptyList(),
    @JsonProperty("WMOidentifier")
    var worldMeteorologicalOrganizationIdList: List<String> = emptyList(),
    var eventEndingTimeList: List<String> = emptyList()
)