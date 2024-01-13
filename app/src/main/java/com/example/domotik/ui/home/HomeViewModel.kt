package com.example.domotik.ui.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domotik.network.model.Weather

class HomeViewModel : ViewModel() {
    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val uiLiveData = MutableLiveData<Weather>()

    // Get the updated text from our model and post the value to MainFragment
    fun getUpdatedText(it:Weather) {
        uiLiveData.postValue(it)
    }



}