package com.example.domotik.network.model

data class WeatherHistory(

    val items: List<Weather>

) {

    fun getSize(): Int{
        return this.items.size
    }

    override fun toString(): String {
        var str = ""
        for ( x in this.items){
            str += x.toString() +"\n"
        }
        return str
    }
}