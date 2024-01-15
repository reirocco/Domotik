package com.example.domotik.network.api

import com.example.domotik.network.model.Weather
import retrofit2.http.GET
import com.example.domotik.ui.util.apiKey

interface WeatherApi {

    @GET("/data/2.5/weather?lat=43.6&lon=12.7&units=Metric&appid="+ apiKey)
    suspend fun curretWeather(): Weather

}