package com.example.domotik.ui.Weather

// FOR MORE SEE https://www.geeksforgeeks.org/android-line-graph-view-with-kotlin/

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.jjoe64.graphview.series.DataPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class WeatherActivity : AppCompatActivity() {

    lateinit var lineGraphView: LineChart
    lateinit var tempChip: Chip

    // Create a viewModel
    private val viewModel: WeatherViewModel = WeatherViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.domotik.R.layout.activity_weather)
        lineGraphView = findViewById<View>(com.example.domotik.R.id.chart) as LineChart
        tempChip = findViewById<Chip>(com.example.domotik.R.id.temp_chip)

        updateGraphObserver()
        getWeatherData()
    }


    // Observer is waiting for viewModel to update our UI
    private fun updateGraphObserver() {
        viewModel.uiLiveData.observe(
            this,
            Observer { update -> updateGraph1(update) })
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

        // Chart initialization
        var lineData : LineData = LineData()

            lineData.addDataSet(dataSetTemp)
        lineData.addDataSet(dataSetCo2)
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
    fun makeLineGraph(weatherHistory: WeatherHistory): Array<ArrayList<DataPoint>> {
        // USING FIRST FOR CO2, SECOND FOR TEMPERATURE, THIRD FOR LUX, FOURTH FOR HUMIDITY AND LAST FOR NOISE
        var datapointArray = arrayOf(
            ArrayList<DataPoint>(),
            ArrayList<DataPoint>(),
            ArrayList<DataPoint>(),
            ArrayList<DataPoint>(),
            ArrayList<DataPoint>()
        )
        //var datetmp:Date
        var datetmp: Double = 0.0
        // ITERATE SINCE ALL RECORD ARE INTO DATAPOINT CLASS

        for (x in weatherHistory.items) {
            //datetmp = Date(givenString_whenCustomFormat_thenLocalDateTimeCreated(x.dt).toEpochSecond(ZoneOffset.UTC))
            datetmp++
            datapointArray[0].add(DataPoint(datetmp, x.main.co2.toDouble()))
            datapointArray[1].add(DataPoint(datetmp, x.main.temp.toDouble()))
            datapointArray[2].add(DataPoint(datetmp, x.main.lux.toDouble()))
            datapointArray[3].add(DataPoint(datetmp, x.main.humidity.toDouble()))
            datapointArray[4].add(DataPoint(datetmp, x.main.noise.toDouble()))
        }
        return datapointArray
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun givenString_whenCustomFormat_thenLocalDateTimeCreated(text: String): LocalDateTime {
        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTime = LocalDateTime.parse(text, pattern)
        return localDateTime
    }


}