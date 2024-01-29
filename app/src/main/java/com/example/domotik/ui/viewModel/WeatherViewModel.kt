package com.example.domotik.ui.viewModel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domotik.R
import com.example.domotik.network.WeatherNetwork
import com.example.domotik.network.model.Weather
import io.grpc.internal.JsonUtil
import kotlinx.coroutines.launch


class WeatherViewModel : ViewModel() {

    val myResponse: MutableLiveData<Weather> = MutableLiveData()

    fun getCurretWeather() {
        viewModelScope.launch {
            try{
                myResponse.value = WeatherNetwork.retrofit.curretWeather()
            }catch (e : Exception){
                throw Exception("Weather exception : " + e.message)
            }

        }
    }

    fun getCurretWeatherWithMetric(units: String) {
        viewModelScope.launch {
            try{
                myResponse.value = WeatherNetwork.retrofit.getWithMetric(units)
            }catch (e : Exception){
                throw Exception("Weather exception : " + e.message)
            }

        }
    }
}