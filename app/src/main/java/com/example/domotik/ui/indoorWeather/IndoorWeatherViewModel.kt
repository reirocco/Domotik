package com.example.domotik.ui.Weather


import androidx.lifecycle.MutableLiveData
import com.example.domotik.network.CustomWeatherNetwork
import com.example.domotik.network.model.WeatherHistory
import androidx.lifecycle.viewModelScope

class IndoorWeatherViewModel {

    val statistics = MutableLiveData<Statistics>()
    val indoorWeatherData = MutableLiveData<WeatherHistory>()
    fun updateIndoorWeatherData(it: WeatherHistory) {
        this.indoorWeatherData.postValue(it)

        if (it != null) {
            var s: Statistics = Statistics()
            for (x in it.items) {
                // maxis
                if (x.main.temp > s.maxTemp) s.maxTemp = x.main.temp
                if (x.main.co2 > s.maxCo2) s.maxCo2 = x.main.co2.toFloat()
                if (x.main.lux > s.maxLux) s.maxLux = x.main.lux
                // minis
                if (x.main.temp < s.minTemp) s.minTemp = x.main.temp
                if (x.main.co2 < s.minCo2) s.minCo2 = x.main.co2.toFloat()
                if (x.main.lux < s.minLux) s.minLux = x.main.lux
                // means
                s.mediaTemp += x.main.temp
                s.mediaCo2 += x.main.co2
                s.mediaLux += x.main.lux
            }
            s.mediaTemp /= it.getSize()
            s.mediaCo2 /= it.getSize()
            s.mediaLux /= it.getSize()
            this.UpdateStatistics(s)
        }
    }

    fun UpdateStatistics(it: Statistics) {
        this.statistics.postValue(it)
    }




}


class Statistics() {
    var maxTemp: Float = -1000.0f
    var minTemp: Float = 1000.0f
    var mediaTemp: Float = 0.0f
    var maxCo2: Float = -1000.0f
    var minCo2: Float = 1000.0f
    var mediaCo2: Float = 0.0f
    var maxLux = -1000.0f
    var minLux: Float = 1000.0f
    var mediaLux: Float = 0.0f
}