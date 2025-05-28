package com.example.customweatherapp.data.api

sealed class APICallResult<T> (
    var successResponse: T? = null,
    var failureResponse: FailureResponse? = null
) {
    class Success<T>(successResponse: T?): APICallResult<T>(successResponse)
    class Failure<T>(successResponse: T? = null, failureResponse: FailureResponse): APICallResult<T>(successResponse, failureResponse)
}