package com.example.domotik.network.model

class Coord(val lat: String, val lon: String)
class MainInfo(val temp:Float, val pressure: Int, val humidity: Int)
data class Weather(
    val name: String,
    val coord: Coord,
    val dt: String,
    val pressure: Int,
    val main: MainInfo
)