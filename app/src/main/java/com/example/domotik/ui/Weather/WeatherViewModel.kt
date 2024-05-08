package com.example.domotik.ui.Weather


import androidx.lifecycle.MutableLiveData
import com.example.domotik.network.CustomWeatherNetwork
import com.example.domotik.network.model.WeatherHistory
import androidx.lifecycle.viewModelScope
class WeatherViewModel {
    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val uiLiveData = MutableLiveData<WeatherHistory>()

    // Get the updated text from our model and post the value to MainFragment
    fun getUpdate(it: WeatherHistory) {
        uiLiveData.postValue(it)
    }

}