package com.example.domotik.ui.messaging

import com.google.firebase.Timestamp

data class Message(
    val senderUid: String,
    val senderName: String,
    val message: String,
    val timestamp: Timestamp
)