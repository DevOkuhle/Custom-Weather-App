package com.example.customweatherapp.data.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Parcelize
data class ParcelableFailureResponse(
    var title: String = "",
    var detail: String = "",
): Parcelable