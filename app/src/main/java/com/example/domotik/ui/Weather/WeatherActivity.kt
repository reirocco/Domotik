package com.example.domotik.ui.Weather

// FOR MORE SEE https://www.geeksforgeeks.org/android-line-graph-view-with-kotlin/

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.domotik.network.model.WeatherHistory
import com.example.domotik.ui.viewModel.WeatherApiViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.jjoe64.graphview.series.DataPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


class WeatherActivity : AppCompatActivity() {

    lateinit var lineGraphView: LineChart
    lateinit var chips: ChipGroup
    lateinit var datePickerI : DatePicker
    lateinit var datePickerF : DatePicker

    // Create a viewModel
    private val viewModel: WeatherViewModel = WeatherViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.domotik.R.layout.activity_weather)
        lineGraphView = findViewById<View>(com.example.domotik.R.id.chart) as LineChart
        chips = findViewById<ChipGroup>(com.example.domotik.R.id.chipGroup)
        datePickerI = findViewById<DatePicker>(com.example.domotik.R.id.init_time)
        datePickerF = findViewById<DatePicker>(com.example.domotik.R.id.last_time)

        updateGraphObserver()
        chipGroupObserver()
        getWeatherData()
    }


    fun updateGraph1(update: WeatherHistory) {

        // Data preparation from class to array
        val tempArray: ArrayList<Entry> = ArrayList<Entry>()
        val co2Array: ArrayList<Entry> = ArrayList<Entry>()
        val luxArray: ArrayList<Entry> = ArrayList<Entry>()
        var i = 0.0
        for (data in update.items) {
            // turn your data into Entry objects
            tempArray.add(Entry(i.toFloat(), data.main.temp))
            co2Array.add(Entry(i.toFloat(), data.main.co2.toFloat()))
            luxArray.add(Entry(i.toFloat(), data.main.lux))
            i++
        }

        // take filter interval
        var interval : Pair<Date,Date>
        datePickerI.month



        // Dataset creation
        var dataSetTemp : LineDataSet = LineDataSet(tempArray, "Temperature")
        dataSetTemp.setColor(1);
        dataSetTemp.setValueTextColor(1); // styling, ...

        // Dataset creation
        var dataSetCo2 : LineDataSet = LineDataSet(co2Array, "Co2")
        dataSetCo2.setColor(1);
        dataSetCo2.setValueTextColor(1); // styling, ...

        // Dataset creation
        var dataSetLux : LineDataSet = LineDataSet(luxArray, "Luminosity")
        dataSetLux.setColor(1);
        dataSetLux.setValueTextColor(1); // styling, ...

        // Getting filter information
        // first: getting chip group and create an arraylist to store the string name of the triggered chip
        val chips : ChipGroup = findViewById<ChipGroup>(com.example.domotik.R.id.chipGroup)
        val filterChip : ArrayList<String> = ArrayList<String>()
        //second: extract checked chips id's and iterate on it to findviewbyid and once get the chip store result on array
        chips.checkedChipIds.forEach { ids ->
            val chip = findViewById<Chip>(ids)
            Log.v("chips", "Chip text: " + chip.text.toString() )
            filterChip.add(chip.text.toString())
        }


        // Chart initialization
        var lineData : LineData = LineData()
        if(filterChip.contains("temp"))
            lineData.addDataSet(dataSetTemp)
        if(filterChip.contains("Co2"))
            lineData.addDataSet(dataSetCo2)
        if(filterChip.contains("lux"))
            lineData.addDataSet(dataSetLux)
        lineGraphView.setData(lineData);
        lineGraphView.invalidate(); // refresh

    }

    fun getWeatherData() {
        Log.v("logging", "entra")
        //Prendo il view model dove memorizzerò i dati
        val weatherApiViewModel = ViewModelProvider(this).get(WeatherApiViewModel::class.java)
        // Recupero i dati dalle API


        weatherApiViewModel.getIndoorWeatherList()  // use this to get indoor data from internal air control station

        // Creo l'observer sul dato da modificare
        weatherApiViewModel.myListResponse.observe(this, Observer {
            Log.v("logging", "passa " + it.toString())
            viewModel.getUpdate(it)
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.v("exception", exception.message.toString())
    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun givenString_whenCustomFormat_thenLocalDateTimeCreated(text: String): LocalDateTime {
        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTime = LocalDateTime.parse(text, pattern)
        return localDateTime
    }


    // Observer is waiting for viewModel to update our UI
    private fun updateGraphObserver() {
        viewModel.uiLiveData.observe(
            this,
            Observer { update -> updateGraph1(update) })
    }

    private fun chipGroupObserver(){
        chips.setOnCheckedStateChangeListener{group, checkedIds ->
            if (checkedIds.isEmpty()){
                Log.v("chips", "nothing selected")
            }else{
                Log.v("chips", "ones or more selected")
                var chipsList : ArrayList<String> = ArrayList<String>()
                checkedIds.forEach { ids ->
                    val chip = findViewById<Chip>(ids)
                    chipsList.add(chip.text.toString())
                    Log.v("chips", "\n\ttriggered value: " + chip.text.toString())
                }
            }
            getWeatherData()
        }
    }



}





