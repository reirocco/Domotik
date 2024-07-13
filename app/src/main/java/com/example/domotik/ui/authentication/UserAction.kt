package com.example.domotik.ui.authentication

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object UserAction {

        private val db = Firebase.firestore

        fun log(email: String, action: String, details: Map<String, Any>) {
            val logEntry = hashMapOf(
                "email" to email,
                "action" to action,
                "details" to details,
                "timestamp" to Timestamp.now()
            )
            db.collection("user_logs")
                .add(logEntry)
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }
        }
    }
