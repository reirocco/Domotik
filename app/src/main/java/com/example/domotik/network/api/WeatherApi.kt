package com.example.domotik.network.api

import com.example.domotik.network.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.domotik.ui.util.apiKey
import retrofit2.http.Path

interface WeatherApi {

    @GET("/data/2.5/weather?lat=43.6&lon=12.7&units=Metric&appid="+ apiKey)
    suspend fun curretWeather(): Weather

    @GET("/data/2.5/weather?lat=43.6&lon=12.7&appid="+ apiKey)
    suspend fun getWithMetric(@Query("units") units: String): Weather

}