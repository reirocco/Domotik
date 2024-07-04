package com.example.domotik.network

import com.example.domotik.network.api.CustomWeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CustomWeatherNetwork {

    //  Take Data from Custom APi
    val retrofitCustomApi by lazy {

        Retrofit.Builder().baseUrl("https://www.montaccini.it")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CustomWeatherApi::class.java)
    }
}