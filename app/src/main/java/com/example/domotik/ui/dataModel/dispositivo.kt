package com.example.domotik.ui.dataModel

import com.google.firebase.Timestamp

data class dispositivo(
    val tipo: String
) {
    data class Lavatrice(
        val email: String,
        val gradi: String,
        val modalita: String,
        val timestamp: com.google.firebase.Timestamp
    )

    data class Forno(
        val temperatura: String,
        val programma: String,
        val timestamp: Timestamp,
        val email: String
    )

    data class Televisione(
        val email: String,
        val canale: Int,
        val volume: Int,
        val timestamp: Timestamp
    )

    data class Lavastoviglie(
        val sale: String,
        val pastiglie: String,
        val modalita: String,
        val timestamp: Timestamp,
        val email: String
    )


}

