package com.example.domotik.ui.homeappliances

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domotik.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class WashingMachineActivity: AppCompatActivity() {
    private lateinit var gradiSpinner: Spinner
    private lateinit var modalitaSpinner: Spinner
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lavatrice_layout)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("/users/vIN3Paf9vPTl9WLEgcSXnWrPxXm2/dispositivi/lavatrice/stato_lavatrice")

        gradiSpinner = findViewById(R.id.gradi_spinner)
        modalitaSpinner = findViewById(R.id.modalità_spinner)
        Log.d("WashingMachineActivity", "gradiSpinner: $gradiSpinner, modalitaSpinner: $modalitaSpinner")



        val gradi_lavatrice = listOf("30", "40", "50", "60")

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, gradi_lavatrice)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gradiSpinner.adapter = adapter1

        val modalita_lavatrice = listOf("Delicati", "Lana", "Sport", "Cotoni")

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, modalita_lavatrice)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        modalitaSpinner.adapter = adapter2

        val saveButton = findViewById<Button>(R.id.set_lavatrice)
        saveButton.setOnClickListener {
            val selectedOption = gradiSpinner.selectedItem.toString()
            val selectedOption2 = modalitaSpinner.selectedItem.toString()
                saveSettingsToFirebase(selectedOption, selectedOption2)
            }}

    private fun saveSettingsToFirebase(selectedOption: String, selectedOption2:String) {
            val settings = hashMapOf(
                "gradi" to selectedOption,
                "modalita" to selectedOption2,
                "timestamp" to System.currentTimeMillis()
            )

            databaseReference.setValue(settings).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Impostazioni salvate", Toast.LENGTH_SHORT).show()
                    val resultIntent = Intent()
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                } else {
                    Toast.makeText(this, "Errore nel salvataggio delle impostazioni", Toast.LENGTH_SHORT).show()
                    Log.d("WashingMachineActivity", "Save button clicked")
                }
            }

    }
    }





