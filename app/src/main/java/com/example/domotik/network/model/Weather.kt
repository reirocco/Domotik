package com.example.domotik.network.model

import android.util.Log

class Coord(val lat: String, val lon: String)
class MainInfo(val temp: Float, val pressure: Int, val humidity: Int)
data class Weather(
    val name: String,
    val coord: Coord,
    val dt: String,
    val pressure: Int,
    val main: MainInfo
) {
    override fun toString(): String {
        return "name:" + this.name + "\ncord\n\tlat: " + this.coord.lat + "\n\tlon: " + this.coord.lon + "\ndt: " + this.dt + "\npressure: " + this.pressure.toString() + "\nmain:\n\ttemp: " + this.main.temp.toString() + "\n\tpressure: " + this.main.pressure.toString() + "\n\thumidity: " + this.main.humidity
    }
}

