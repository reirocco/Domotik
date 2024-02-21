package com.example.domotik.network.api

import com.example.domotik.network.model.Weather
import com.example.domotik.network.model.WeatherHistory
import com.example.domotik.ui.util.apiKey
import retrofit2.http.GET

interface CustomWeatherApi {
    @GET("IEQ/API/get_raw_list.php?username=raspberrypiIEQ&password=123456789")
    suspend fun weatherHisory(): WeatherHistory

    @GET("IEQ/API/get_last_raw.php?username=raspberrypiIEQ&password=123456789")
    suspend fun weatherLast(): Weather

}