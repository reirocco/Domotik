package com.example.domotik.ui.messaging

import com.google.firebase.Timestamp

data class Message(
    val message: String,
    val timestamp: Timestamp
)