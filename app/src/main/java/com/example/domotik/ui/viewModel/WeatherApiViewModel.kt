package com.example.domotik.ui.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domotik.network.WeatherNetwork
import com.example.domotik.network.model.Weather
import kotlinx.coroutines.launch


class WeatherApiViewModel : ViewModel() {

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