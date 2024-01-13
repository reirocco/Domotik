package com.example.domotik.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domotik.network.WeatherNetwork
import com.example.domotik.network.model.Weather
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    val myResponse: MutableLiveData<Weather> = MutableLiveData()

    fun getCurretWeather() {
        viewModelScope.launch {
            myResponse.value = WeatherNetwork.retrofit.curretWeather()
        }
    }
}