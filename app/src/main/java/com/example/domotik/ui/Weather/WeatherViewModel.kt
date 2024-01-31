package com.example.domotik.ui.Weather

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.domotik.network.model.Weather

class WeatherViewModel {
    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val uiLiveData = MutableLiveData<WeatherManagerModel>()

    // Get the updated text from our model and post the value to MainFragment
    fun getUpdate(it: WeatherManagerModel) {
        uiLiveData.postValue(it)
    }

}