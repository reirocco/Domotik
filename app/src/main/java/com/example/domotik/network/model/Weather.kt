package com.example.domotik.network.model

class Coord(val lat: String, val lon: String)
class MainInfo(
    val temp: Float,
    val pressure: Int,
    val humidity: Int,
    val co2: Int = 0,
    val noise: Float = 0.0f,
    val lux: Float = 0.0f
)

data class Weather(
    val name: String,
    val coord: Coord,
    val dt: String,
    val main: MainInfo
) {
    override fun toString(): String {
        return "name:" + this.name + "\ncord\n\tlat: " + this.coord.lat + "\n\tlon: " + this.coord.lon + "\ndt: " + this.dt + "\nmain:\n\ttemp: " + this.main.temp.toString() + "\n\tpressure: " + this.main.pressure.toString() + "\n\thumidity: " + this.main.humidity + "\n\tco2: " + this.main.co2.toString() + "\n\tnoise: " + this.main.noise.toString() + "\n\tlux: " + this.main.lux.toString()
    }
}

