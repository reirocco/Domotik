package com.example.domotik.ui.Weather

// FOR MORE SEE https://www.geeksforgeeks.org/android-line-graph-view-with-kotlin/

import GsonRequest
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.domotik.network.model.WeatherHistory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


class IndoorWeatherActivity : AppCompatActivity() {


    // Create a viewModel
    private val indoorViewModel: IndoorWeatherViewModel = IndoorWeatherViewModel()
    private val pattern = "yyyy-MM-dd"
    private val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault()) // Crea un oggetto SimpleDateFormat con il pattern


    lateinit var table: TableLayout
    lateinit var lineGraphView: LineChart
    lateinit var chips: ChipGroup
    lateinit var submitDateButton: Button
    lateinit var chipsList: ArrayList<String>
    lateinit var dateInitButton: Button
    lateinit var dateEndButton: Button
    lateinit var dateInit :Date
    lateinit var dateEnd :Date

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.domotik.R.layout.activity_indoor_weather)



        this.table = findViewById(com.example.domotik.R.id.tableLayout)
        lineGraphView = findViewById<View>(com.example.domotik.R.id.chart) as LineChart
        chips = findViewById<ChipGroup>(com.example.domotik.R.id.chipGroup)
        submitDateButton = findViewById<Button>(com.example.domotik.R.id.submitButton)
        this.dateInitButton = findViewById<Button>(com.example.domotik.R.id.dateInit)
        this.dateEndButton = findViewById<Button>(com.example.domotik.R.id.dateEnd)

        chipGroupObserver()

        // Observer
        this.indoorViewModel.statistics.observe(this, { it ->
            updateTable(it)
        })

        this.indoorViewModel.indoorWeatherData.observe(this, { it ->
            updateGraph(it)
        })

        this.submitDateButton.setOnClickListener { it ->
            showDatePickerDialog{ date ->
                Log.v("date", date.toString())
            }
        }

        this.dateInitButton.setOnClickListener {
            showDatePickerDialog{ date ->
                Log.v("date", "date init -> "+date.toString())
            }
        }

        this.dateEndButton.setOnClickListener {
            showDatePickerDialog{ date ->
                Log.v("date", "date end -> "+date.toString())
            }
        }


        // Ottieni la data corrente
        this.dateInit = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        this.dateEnd = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        this.dateInitButton.setText(simpleDateFormat.format(this.dateInit).toString())
        this.dateEndButton.setText(simpleDateFormat.format(this.dateInit).toString())

        // take DateFilter interval
        /*val interval: Pair<Date, Date> = Pair<Date, Date>(
            Date(2024, datePickerI.month, datePickerI.dayOfMonth),
            Date(2024, datePickerF.month, datePickerF.dayOfMonth)
        )

        Log.v("date",simpleDateFormat.format(interval.first).toString())*/
        doRequest()
    }

    fun updateGraph(update: WeatherHistory) {
        // take filter interval
        /*val interval: Pair<Date, Date> = Pair<Date, Date>(
            Date(2024, datePickerI.month, datePickerI.dayOfMonth),
            Date(2024, datePickerF.month, datePickerF.dayOfMonth)
        )*/        //Log.v("date", interval.first.toString() + " - " + interval.second.toString())
        // Data preparation from class to array
        val tempArray: ArrayList<Entry> = ArrayList<Entry>()
        val co2Array: ArrayList<Entry> = ArrayList<Entry>()
        val luxArray: ArrayList<Entry> = ArrayList<Entry>()
        var i = 0.0
        // Temperature, Co2, Lux
        Log.v("logging", "calcolo...")
        for (data in update.items) {
            // turn your data into Entry objects
            tempArray.add(Entry(i.toFloat(), data.main.temp))
            co2Array.add(Entry(i.toFloat(), data.main.co2.toFloat()))
            luxArray.add(Entry(i.toFloat(), data.main.lux))
            i++
        }


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
        if (filterChip.contains("temp")) lineData.addDataSet(dataSetTemp)
        if (filterChip.contains("Co2")) lineData.addDataSet(dataSetCo2)
        if (filterChip.contains("lux")) lineData.addDataSet(dataSetLux)

        val desc: Description = Description()
        desc.text = "Indoor Quality Data "
        lineGraphView.setDescription(desc)
        lineGraphView.setData(lineData);
        lineGraphView.invalidate(); // refresh

    }


    fun convertDate(stringDate: String, pattern: String): Date {
        val formatter = SimpleDateFormat(pattern)
        val date = formatter.parse(stringDate)
        return date
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
        Log.v("date","https://www.montaccini.it/IEQ/API/get_raw_list.php?username=raspberrypiIEQ&password=123456789&init="+simpleDateFormat.format(this.dateInit).toString()+"&end="+simpleDateFormat.format(this.dateEnd).toString())
        val myReq: GsonRequest<WeatherHistory> = GsonRequest<WeatherHistory>(
            "https://www.montaccini.it/IEQ/API/get_raw_list.php?username=raspberrypiIEQ&password=123456789&init="+simpleDateFormat.format(this.dateInit).toString()+"&end="+simpleDateFormat.format(this.dateEnd).toString(),
            WeatherHistory::class.java,
            null,
            createMyReqSuccessListener(),
            createMyReqErrorListener()
        )

        queue.add(myReq)
    }

    private fun createMyReqSuccessListener(): Response.Listener<WeatherHistory> {
        return Response.Listener<WeatherHistory> { it ->
            this.indoorViewModel.updateIndoorWeatherData(it)
        }
    }

    private fun createMyReqErrorListener(): Response.ErrorListener {
        return Response.ErrorListener {
            // Do whatever you want to do with error.getMessage();
        }
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
                    this.indoorViewModel.indoorWeatherData.value?.let { updateGraph(it) }
                }
            }
        }

    }


    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Formatter la data selezionata
                calendar.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(calendar.time)
                onDateSelected(formattedDate)
            },
            year, month, day
        )

        datePickerDialog.show()
    }


}

