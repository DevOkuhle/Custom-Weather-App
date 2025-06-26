package com.example.customweatherapp.util

import com.example.customweatherapp.data.api.APICallResult
import com.example.customweatherapp.data.api.FailureResponse
import com.example.customweatherapp.data.api.ParcelableFailureResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Response

class APISetup {

    fun <T> populateResponseFromWeatherAPI(apiFunctionCall: suspend () -> Response<T>): Flow<APICallResult<T>> {
        lateinit var customWeatherResponse: Response<T>
        return flow {
            try {
                customWeatherResponse = apiFunctionCall()
                if (!customWeatherResponse.isSuccessful) {
                    val failureResponse: FailureResponse = parseErrorBody(Json { ignoreUnknownKeys = true }, customWeatherResponse.errorBody()) ?: FailureResponse()
                    val parcelableFailureResponse = ParcelableFailureResponse(
                        title = failureResponse.title,
                        detail = failureResponse.detail
                    )
                    emit(APICallResult.Failure(failureResponse = parcelableFailureResponse))
                    return@flow
                }
            } catch (e: Exception) {
                e.printStackTrace()
                val networkErrorMessage = e.message ?: ""
                emit(APICallResult.Failure(failureResponse = ParcelableFailureResponse(detail = networkErrorMessage)))
                return@flow
            }
            emit(APICallResult.Success(successResponse = customWeatherResponse.body()))
        }
    }

    private fun parseErrorBody(json: Json, errorBody: ResponseBody?): FailureResponse? {
        return try {
            errorBody?.string()?.let { json.decodeFromString<FailureResponse>(it) }
        } catch (e: Exception) {
            null
        }
    }
}