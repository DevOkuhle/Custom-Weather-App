package com.example.customweatherapp.data.api

data class FailureResponse(
    var type: String = "",
    var title: String = "",
    var status: Int = -1,
    var detail: String = "",
    var instance: String = "",
    var correlationId: String = "",
    var additionalProp1: Any? = null
)