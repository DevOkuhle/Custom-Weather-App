package com.example.customweatherapp.util

import com.example.customweatherapp.data.api.APICallResult
import com.example.customweatherapp.data.api.FailureResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class APISetup {

    fun <T> populateResponseFromWeatherAPI(apiFunctionCall: suspend () -> Response<T>): Flow<APICallResult<T>> {
        lateinit var customWeatherResponse: Response<T>
        val gsonObject = Gson()
        return flow {
            try {
                customWeatherResponse = apiFunctionCall()
                if (!customWeatherResponse.isSuccessful) {
                    val failureErrorBody = customWeatherResponse.errorBody()?.toString() ?: ""
                    val failureResponse: FailureResponse =
                        gsonObject.fromJson(failureErrorBody, FailureResponse::class.java)
                    emit(APICallResult.Failure(failureResponse = failureResponse))
                    return@flow
                }
            } catch (e: Exception) {
                e.printStackTrace()
                val networkErrorMessage = e.message ?: ""
                emit(APICallResult.Failure(failureResponse = FailureResponse(detail = networkErrorMessage)))
                return@flow
            }
            emit(APICallResult.Success(successResponse = customWeatherResponse.body()))
        }
    }
}