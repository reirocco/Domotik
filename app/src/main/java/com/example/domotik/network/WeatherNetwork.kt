package com.example.domotik.network

import com.example.domotik.network.api.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherNetwork {

    //  Take Data from OpenWeatherMap
    val retrofitOpenWeather by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

}