package com.example.domotik.ui.Weather

// FOR MORE SEE https://www.geeksforgeeks.org/android-line-graph-view-with-kotlin/

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.domotik.network.model.WeatherHistory
import com.example.domotik.ui.viewModel.WeatherApiViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.CoroutineExceptionHandler
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import com.github.mikephil.charting.data.Entry
import java.text.SimpleDateFormat
import kotlin.math.log


class WeatherActivity : AppCompatActivity() {

    lateinit var lineGraphView: LineChart
    lateinit var chips: ChipGroup
    lateinit var datePickerI: DatePicker
    lateinit var datePickerF: DatePicker
    lateinit var submitDateButton: Button
    lateinit var chipsList: ArrayList<String>
    //statistics
    lateinit var maxTempText: TextView
    lateinit var maxCo2Text: TextView
    lateinit var maxLuxText: TextView
    lateinit var minTempText: TextView
    lateinit var minCo2Text: TextView
    lateinit var minLuxText: TextView
    lateinit var mediaTempText: TextView
    lateinit var mediaCo2Text: TextView
    lateinit var mediaLuxText: TextView

    // Create a viewModel
    private val viewModel: WeatherViewModel = WeatherViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.domotik.R.layout.activity_weather)
        lineGraphView = findViewById<View>(com.example.domotik.R.id.chart) as LineChart
        chips = findViewById<ChipGroup>(com.example.domotik.R.id.chipGroup)
        datePickerI = findViewById<DatePicker>(com.example.domotik.R.id.init_time)
        datePickerF = findViewById<DatePicker>(com.example.domotik.R.id.last_time)
        submitDateButton = findViewById<Button>(com.example.domotik.R.id.submitButton)
        //statistics
        maxTempText = findViewById(com.example.domotik.R.id.tempMax)
        maxCo2Text = findViewById(com.example.domotik.R.id.co2Max)
        maxLuxText = findViewById(com.example.domotik.R.id.luxMax)
        minTempText = findViewById(com.example.domotik.R.id.tempMin)
        minCo2Text = findViewById(com.example.domotik.R.id.co2Min)
        minLuxText = findViewById(com.example.domotik.R.id.luxMin)
        mediaTempText = findViewById(com.example.domotik.R.id.tempMedia)
        mediaCo2Text = findViewById(com.example.domotik.R.id.co2Media)
        mediaLuxText = findViewById(com.example.domotik.R.id.luxMedia)

        updateGraphObserver()
        //chipGroupObserver()
        datePickerObserver()
        getWeatherData()
    }


    fun updateGraph1(update: WeatherHistory) {
        // take filter interval
        val interval: Pair<Date, Date> = Pair<Date, Date>(
            Date(2024, datePickerI.month, datePickerI.dayOfMonth),
            Date(2024, datePickerF.month, datePickerF.dayOfMonth)
        )
        //Log.v("date", interval.first.toString() + " - " + interval.second.toString())
        // Data preparation from class to array
        val tempArray: ArrayList<Entry> = ArrayList<Entry>()
        val co2Array: ArrayList<Entry> = ArrayList<Entry>()
        val luxArray: ArrayList<Entry> = ArrayList<Entry>()
        var i = 0.0
        // Temperature, Co2, Lux
        var (tempMax,co2Max,luxMax) = Triple<Float,Int,Float>(-1000.0F,-1000,-1000.0F)
        var (tempMin,co2Min,luxMin) = Triple<Float,Int,Float>(1000.0F,1000,1000.0F)
        var (tempMedia,co2Media,luxMedia) = Triple<Float,Int,Float>(0.0F,0,0.0F)
        Log.v("logging", "calcolo...")
        for (data in update.items) {
            /*Log.v(
                "datefirst",
                interval.first.toString() + " <-> " + convertDate(data.dt) + " --> " + (interval.first.month <= convertDate(
                    data.dt.toString()
                ).month && interval.first.day <= convertDate(data.dt.toString()).day).toString()
            )*/
            if (interval.first.month <= convertDate(data.dt.toString()).month && interval.first.day <= convertDate(
                    data.dt.toString()
                ).day
            ) {
                /*Log.v(
                    "datesecond",
                    interval.second.toString() + " <-> " + convertDate(data.dt) + " --> " + (interval.second.month >= convertDate(
                        data.dt.toString()
                    ).month && interval.second.day >= convertDate(data.dt.toString()).day).toString()
                )*/
                if (interval.second.month >= convertDate(data.dt.toString()).month && interval.second.day >= convertDate(
                        data.dt.toString()
                    ).day
                ) {
                    // search max and min temp
                    if (tempMax < data.main.temp)
                    {
                        tempMax = data.main.temp
                    }
                    if (tempMin > data.main.temp){
                        tempMin = data.main.temp
                    }
                    // search max and min co2
                    if (co2Max < data.main.co2)
                    {
                        co2Max = data.main.co2
                    }
                    if (co2Min > data.main.co2){
                        co2Min = data.main.co2
                    }
                    // search max and min lux
                    if (luxMax < data.main.lux)
                    {
                        luxMax = data.main.lux
                    }
                    if (tempMin > data.main.lux){
                        luxMin = data.main.lux
                    }
                    tempMedia = tempMedia + data.main.temp
                    co2Media = co2Media + data.main.co2
                    luxMedia = luxMedia + data.main.lux
                    // turn your data into Entry objects
                    tempArray.add(Entry(i.toFloat(), data.main.temp))
                    co2Array.add(Entry(i.toFloat(), data.main.co2.toFloat()))
                    luxArray.add(Entry(i.toFloat(), data.main.lux))
                    i ++
                }
            }

        }

        Log.v("logging", "Min - Max temp "+ tempMin + " - " +tempMax)
        Log.v("logging", "Min - Max co2 "+ co2Min + " - " +co2Max)
        Log.v("logging", "Min - Max lux "+ luxMin + " - " +luxMax)

        //statistics creation
        maxTempText.setText(tempMax.toString())
        maxCo2Text.setText(co2Max.toString())
        maxLuxText.setText(luxMax.toString())
        minTempText.setText(tempMin.toString())
        minCo2Text.setText(co2Min.toString())
        minLuxText.setText(luxMin.toString())
        mediaTempText.setText(tempMedia.toString())
        mediaCo2Text.setText(co2Media.toString())
        mediaLuxText.setText(luxMedia.toString())
        // Dataset creation
        var dataSetTemp: LineDataSet = LineDataSet(tempArray, "Temperature")
        dataSetTemp.setColor(1);
        dataSetTemp.setValueTextColor(1); // styling, ...

        // Dataset creation
        var dataSetCo2: LineDataSet = LineDataSet(co2Array, "Co2")
        dataSetCo2.setColor(2);
        dataSetCo2.setValueTextColor(2); // styling, ...

        // Dataset creation
        var dataSetLux: LineDataSet = LineDataSet(luxArray, "Luminosity")
        dataSetLux.setColor(3);
        dataSetLux.setValueTextColor(3); // styling, ...

        // Getting filter information
        // first: getting chip group and create an arraylist to store the string name of the triggered chip
        val chips: ChipGroup = findViewById<ChipGroup>(com.example.domotik.R.id.chipGroup)
        val filterChip: ArrayList<String> = ArrayList<String>()
        //second: extract checked chips id's and iterate on it to findviewbyid and once get the chip store result on array
        chips.checkedChipIds.forEach { ids ->
            val chip = findViewById<Chip>(ids)
            //Log.v("chips", "Chip text: " + chip.text.toString() )
            filterChip.add(chip.text.toString())
        }


        // Chart initialization
        var lineData: LineData = LineData()
        if (filterChip.contains("temp"))
            lineData.addDataSet(dataSetTemp)
        if (filterChip.contains("Co2"))
            lineData.addDataSet(dataSetCo2)
        if (filterChip.contains("lux"))
            lineData.addDataSet(dataSetLux)

        val desc:Description = Description()
        desc.text = "Indoor Quality Data "
        lineGraphView.setDescription(desc)
        lineGraphView.setData(lineData);
        lineGraphView.invalidate(); // refresh

    }


    fun convertDate(stringDate: String): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = formatter.parse(stringDate)
        return date
    }

    fun getWeatherData() {
        Log.v("logging", "recupero dati...")
        //Prendo il view model dove memorizzerò i dati
        val weatherApiViewModel = ViewModelProvider(this).get(WeatherApiViewModel::class.java)
        // Recupero i dati dalle API


        weatherApiViewModel.getIndoorWeatherList()  // use this to get indoor data from internal air control station

        // Creo l'observer sul dato da modificare
        weatherApiViewModel.myListResponse.observe(this, Observer {
            //Log.v("logging", "passa " + it.toString())
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

    private fun chipGroupObserver() {
        chips.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isEmpty()) {
                Log.v("chips", "nothing selected")
            } else {
                Log.v("chips", "ones or more selected")
                this.chipsList = ArrayList<String>()
                checkedIds.forEach { ids ->
                    val chip = findViewById<Chip>(ids)
                    chipsList.add(chip.text.toString())
                    Log.v("chips", "\n\ttriggered value: " + chip.text.toString())
                }
            }
        }
    }

    private fun datePickerObserver() {
        Log.v("logging", "aggiorno...")
        datePickerI.init(2024, 2, 5, null)
        datePickerI.updateDate(2024,2,5)
        datePickerF.init(2024, 2, 6, null)
        datePickerF.updateDate(2024,2,6)
        submitDateButton.setOnClickListener({ view ->

            viewModel.uiLiveData.value?.let { updateGraph1(it) }
        })

    }

}

