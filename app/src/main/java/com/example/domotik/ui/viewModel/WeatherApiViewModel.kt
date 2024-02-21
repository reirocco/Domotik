/*
 * *
 *  * Created by norir on 21/02/24, 12:57
 *  * Copyright (c) 2024 . All rights reserved.
 *  * Last modified 21/02/24, 12:57
 *  * Description:
 *
 */

package com.example.domotik.ui.viewModel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domotik.network.WeatherNetwork
import com.example.domotik.network.CustomWeatherNetwork
import com.example.domotik.network.model.Weather
import com.example.domotik.network.model.WeatherHistory
import kotlinx.coroutines.launch


class WeatherApiViewModel : ViewModel() {

    val myCurrentResponse: MutableLiveData<Weather> = MutableLiveData()
    val myListResponse: MutableLiveData<WeatherHistory> = MutableLiveData()


    fun getCurretWeather() {
        viewModelScope.launch {
            try{
                myCurrentResponse.value = WeatherNetwork.retrofitOpenWeather.curretWeather()
            }catch (e : Exception){
                throw Exception("Weather exception : " + e.message)
            }

        }
    }

    fun getCurretWeatherWithMetric(units: String) {
        viewModelScope.launch {
            try{
                myCurrentResponse.value = WeatherNetwork.retrofitOpenWeather.getWithMetric(units)
            }catch (e : Exception){
                throw Exception("Weather exception : " + e.message)
            }

        }
    }

    //  Retrive data from Custom Api
    fun getIndoorWeatherList(){
        viewModelScope.launch {
            try{
                myCurrentResponse.value = CustomWeatherNetwork.retrofitCustomApi.weatherLast()
            }catch (e : Exception){
                throw Exception("Weather exception : " + e.message)
            }

        }
    }

    fun getIndoorCurrentWeather(){
        viewModelScope.launch {
            try{
                //myResponse.value = WeatherNetwork.retrofitOpenWeather.getWithMetric("metric")
                Log.v( "logging" ,CustomWeatherNetwork.retrofitCustomApi.weatherHisory().toString())
            }catch (e : Exception){
                throw Exception("Weather exception : " + e.message)
            }

        }
    }


}