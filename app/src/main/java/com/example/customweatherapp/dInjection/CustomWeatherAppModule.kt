package com.example.customweatherapp.dInjection

import com.example.customweatherapp.data.api.CustomWeatherAPI
import com.example.customweatherapp.repository.CustomWeatherRepository
import com.example.customweatherapp.repository.CustomWeatherRepositoryImpl
import com.example.customweatherapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CustomWeatherAppModule {

    @Provides
    @Singleton
    fun providesWeatherAPI(): CustomWeatherAPI {
        val httpInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.WEATHER_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(CustomWeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesCustomWeatherRepository(customWeatherAPI: CustomWeatherAPI): CustomWeatherRepository = CustomWeatherRepositoryImpl(customWeatherAPI)
}