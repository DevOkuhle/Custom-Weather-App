package com.example.customweatherapp.dInjection

import android.app.Application
import androidx.room.Room
import com.example.customweatherapp.data.api.CustomWeatherAPI
import com.example.customweatherapp.repository.CustomWeatherRepository
import com.example.customweatherapp.repository.CustomWeatherRepositoryImpl
import com.example.customweatherapp.room.CustomWeatherDao
import com.example.customweatherapp.room.CustomWeatherDatabase
import com.example.customweatherapp.util.Constants
import com.example.customweatherapp.util.Constants.Companion.CUSTOM_WEATHER_DATABASE
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CustomWeatherAppModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun providesWeatherAPI(): CustomWeatherAPI {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val httpInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.WEATHER_API_BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/ld+json".toMediaType()))
            .client(httpClient)
            .build()
            .create(CustomWeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesCustomWeatherDatabase(appContext: Application): CustomWeatherDatabase {
        return  Room.databaseBuilder(
            appContext.applicationContext,
            CustomWeatherDatabase::class.java,
            CUSTOM_WEATHER_DATABASE
        ).build()
    }

    @Provides
    fun provideUserDao(customWeatherDatabase: CustomWeatherDatabase): CustomWeatherDao  = customWeatherDatabase.customWeatherDao()

    @Provides
    @Singleton
    fun providesCustomWeatherRepository(customWeatherAPI: CustomWeatherAPI, customWeatherDao: CustomWeatherDao): CustomWeatherRepository {
        return CustomWeatherRepositoryImpl(customWeatherAPI, customWeatherDao)
    }
}