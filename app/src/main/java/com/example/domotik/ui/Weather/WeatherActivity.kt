package com.example.domotik.ui.Weather

// FOR MORE SEE https://www.geeksforgeeks.org/android-line-graph-view-with-kotlin/

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.domotik.R
import com.example.domotik.network.model.WeatherHistory
import com.example.domotik.ui.util.ChartView
import com.example.domotik.ui.viewModel.WeatherApiViewModel
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.CoroutineExceptionHandler
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.math.log


class WeatherActivity : AppCompatActivity() {

    lateinit var lineGraphView: GraphView

    // Create a viewModel
    private val viewModel: WeatherViewModel = WeatherViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        var chart = ChartView(this)
        //setContentView(ChartView(this))
        /*
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Weather Data"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        lineGraphView = findViewById(R.id.idGraphView)   // on below line we are initializing
        updateGraphObserver()
        getWeatherData()
        */
    }


    // Observer is waiting for viewModel to update our UI
    private fun updateGraphObserver() {
        viewModel.uiLiveData.observe(
            this,
            Observer { update -> updateGraph1(update) })
    }

    fun updateGraph1(update: WeatherHistory) {
        // below we are adding data to our graph view.
        //  FIRST MAKE A DATAPOINT COLLECTION WITH THE INDOOR WEATHER DATA
        var datapointarray: Array<ArrayList<DataPoint>> = arrayOf()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datapointarray = makeLineGraph(update)
        }

        // DEFINING A TMP ARRAY TO EXTRACT ARRAYS
        var arrayof: Array<DataPoint> = datapointarray.get(3)
            .toTypedArray()    // RETURN ARRAY MUST BE TYPED, CASTING IS NOT ACCEPTING ANYWAY
        for (x in arrayof)
            Log.v("logging", "x: " + x.x.toString() + " y:" + x.y.toString())
        val series: LineGraphSeries<DataPoint> = LineGraphSeries(arrayof)

        // on below line adding animation
        lineGraphView.animate()

        // on below line we are setting scrollable
        // for point graph view
        lineGraphView.viewport.isScrollable = true

        // on below line we are setting scalable.
        lineGraphView.viewport.isScalable = true

        // on below line we are setting scalable y
        lineGraphView.viewport.setScalableY(false)

        // on below line we are setting scrollable y
        lineGraphView.viewport.setScrollableY(false)

        // on below line we are setting color for series.
        series.color = R.color.purple_200

        // on below line we are adding
        // data series to our graph view.
        lineGraphView.addSeries(series)
        lineGraphView.addSeries(LineGraphSeries<DataPoint>(arrayOf(DataPoint(1.0, 0.0))))

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