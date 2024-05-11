/*
 * *
 *  * Created by norir on 08/05/24, 14:54
 *  * Copyright (c) 2024 . All rights reserved.
 *  * Last modified 08/05/24, 14:54
 *  * Description:
 *
 */

package com.example.domotik.ui.util

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.data2viz.charts.chart.Chart
import io.data2viz.charts.chart.chart
import io.data2viz.charts.chart.discrete
import io.data2viz.charts.chart.mark.area
import io.data2viz.charts.chart.quantitative
import io.data2viz.geom.Size
import io.data2viz.viz.VizContainerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ChartView(this))
    }
}

class ChartView(context: Context) : VizContainerView(context) {

    private val chart: Chart<PopCount> = chart(canPop) {
        size = Size(vizSize, vizSize)
        title = "Population of Canada 1851–2001 (Statistics Canada)"

        // Create a discrete dimension for the year of the census
        val year = discrete({ domain.year })

        // Create a continuous numeric dimension for the population
        val population = quantitative({ domain.population }) {
            name = "Population of Canada (in millions)"
        }

        // Using a discrete dimension for the X-axis and a continuous one for the Y-axis
        area(year, population)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        chart.size = Size(vizSize, vizSize * h / w)
    }
}

const val vizSize = 500.0

data class PopCount(val year: Int, val population: Double)

val canPop = listOf(
    PopCount(1851, 2.436),
    PopCount(1861, 3.23),
    PopCount(1871, 3.689),
    PopCount(1881, 4.325),
    PopCount(1891, 4.833),
    PopCount(1901, 5.371),
    PopCount(1911, 7.207),
    PopCount(1921, 8.788),
    PopCount(1931, 10.377),
    PopCount(1941, 11.507),
    PopCount(1951, 13.648),
    PopCount(1961, 17.78),
    PopCount(1971, 21.046),
    PopCount(1981, 23.774),
    PopCount(1991, 26.429),
    PopCount(2001, 30.007)
)