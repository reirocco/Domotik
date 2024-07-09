package com.example.domotik.ui.Weather

// FOR MORE SEE https://www.geeksforgeeks.org/android-line-graph-view-with-kotlin/

import GsonRequest
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.domotik.network.model.WeatherHistory


class IndoorWeatherActivity : AppCompatActivity() {


    // Create a viewModel
    private val indoorViewModel: IndoorWeatherViewModel = IndoorWeatherViewModel()
    lateinit var table: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.domotik.R.layout.activity_indoor_weather)

        // Observer
        this.indoorViewModel.statistics.observe(this, { it ->
            updateTable(it)
        })

        this.table = findViewById(com.example.domotik.R.id.tableLayout)

        doRequest()
        //updateTable(Statistics())
    }

    fun updateTable(data: Statistics) {
        var tableRow: TableRow
        var cell: TextView
        // Row 1
        tableRow = this.table.getChildAt(1) as TableRow
        cell = tableRow.getChildAt(1) as TextView
        cell.text = data.minTemp.toString()
        cell = tableRow.getChildAt(2) as TextView
        cell.text = data.maxTemp.toString()
        cell = tableRow.getChildAt(3) as TextView
        cell.text = "%.2f".format(data.mediaTemp)
        // Row 2
        tableRow = this.table.getChildAt(2) as TableRow
        cell = tableRow.getChildAt(1) as TextView
        cell.text = data.minCo2.toString()
        cell = tableRow.getChildAt(2) as TextView
        cell.text = data.maxCo2.toString()
        cell = tableRow.getChildAt(3) as TextView
        cell.text = "%.2f".format(data.mediaCo2)
        // Row 3
        tableRow = this.table.getChildAt(3) as TableRow
        cell = tableRow.getChildAt(1) as TextView
        cell.text = data.minLux.toString()
        cell = tableRow.getChildAt(2) as TextView
        cell.text = "%.2f".format(data.maxLux)
        cell = tableRow.getChildAt(3) as TextView
        cell.text = "%.2f".format(data.mediaLux)

    }


    fun doRequest() {
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val myReq: GsonRequest<WeatherHistory> = GsonRequest<WeatherHistory>(
            "https://www.montaccini.it/IEQ/API/get_raw_list.php?username=raspberrypiIEQ&password=123456789",
            WeatherHistory::class.java,
            null,
            createMyReqSuccessListener(),
            createMyReqErrorListener()
        )

        queue.add(myReq)
    }

    private fun createMyReqSuccessListener(): Response.Listener<WeatherHistory> {
        return Response.Listener<WeatherHistory> {it ->
            this.indoorViewModel.updateIndoorWeatherData(it)
        }
    }

    private fun createMyReqErrorListener(): Response.ErrorListener {
        return Response.ErrorListener {
            // Do whatever you want to do with error.getMessage();
        }
    }

}

