package com.example.domotik.network.api

import com.example.domotik.network.model.Weather
import retrofit2.http.GET

interface WeatherApi {

    @GET("/data/2.5/weather?lat=43.6&lon=12.7&appid=532ee89430c0bf4f8f75f15176039cdc")
    suspend fun curretWeather(): Weather

}