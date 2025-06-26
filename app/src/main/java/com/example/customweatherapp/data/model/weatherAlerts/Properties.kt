package com.example.customweatherapp.data.model.weatherAlerts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Properties(
    @SerialName("@id")
    val atId: String?,
    @SerialName("@type")
    val atType: String?,
    val id: String?,
    @SerialName("areaDesc")
    val areaDescription: String?,
    val geocode: Geocode?,
    val affectedZones: List<String>?,
    val references: List<JsonElement>?,
    val sent: String?,
    val effective: String?,
    val onset: String?,
    val expires: String?,
    val ends: String?,
    val status: String?,
    val messageType: String?,
    val category: String?,
    val severity: String?,
    val certainty: String?,
    val urgency: String?,
    val event: String?,
    val sender: String?,
    val senderName: String?,
    val headline: String?,
    val description: String?,
    val instruction: String?,
    val response: String?,
    val parameters: Parameters?,
    val scope: String?,
    val code: String?,
    val language: String?,
    val web: String?,
    val eventCode: EventCode?
)