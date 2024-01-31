package com.example.domotik.ui.Weather

// FOR MORE SEE https://www.geeksforgeeks.org/android-line-graph-view-with-kotlin/

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.domotik.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class WeatherActivity : AppCompatActivity() {

    lateinit var lineGraphView: GraphView

    // Create a viewModel
    private val viewModel: WeatherViewModel = WeatherViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Weather Data"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        lineGraphView = findViewById(R.id.idGraphView)   // on below line we are initializing
        updateGraphObserver()
        viewModel.getUpdate(WeatherManagerModel())


    }




    // Observer is waiting for viewModel to update our UI
    private fun updateGraphObserver() {
        viewModel.uiLiveData.observe(
            this,
            Observer { update -> updateGraph1(update) })
    }

    fun updateGraph1(update: WeatherManagerModel) {
        // on below line we are adding data to our graph view.
        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                // on below line we are adding
                // each point on our x and y axis.
                DataPoint(0.0, 1.0),
                DataPoint(1.0, 3.0),
                DataPoint(2.0, 4.0),
                DataPoint(3.0, 9.0),
                DataPoint(4.0, 6.0),
                DataPoint(5.0, 3.0),
                DataPoint(6.0, 6.0),
                DataPoint(7.0, 1.0),
                DataPoint(8.0, 2.0)
            )
        )

        // on below line adding animation
        lineGraphView.animate()

        // on below line we are setting scrollable
        // for point graph view
        lineGraphView.viewport.isScrollable = true

        // on below line we are setting scalable.
        lineGraphView.viewport.isScalable = false

        // on below line we are setting scalable y
        lineGraphView.viewport.setScalableY(false)

        // on below line we are setting scrollable y
        lineGraphView.viewport.setScrollableY(false)

        // on below line we are setting color for series.
        series.color = R.color.purple_200

        // on below line we are adding
        // data series to our graph view.
        lineGraphView.addSeries(series)

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}