package com.example.domotik.ui.homeappliances

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domotik.R
import com.example.domotik.ui.dataModel.dispositivo
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class OvenActivity : AppCompatActivity() {
    private lateinit var temperaturaSpinner: Spinner
    private lateinit var programmaSpinner: Spinner
    private lateinit var impostazioni_forno: TextView
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forno_layout)

        temperaturaSpinner = findViewById(R.id.temperatura_spinner)
        programmaSpinner = findViewById(R.id.programma_spinner)
        impostazioni_forno = findViewById(R.id.impostazioni_verifica)
        Log.d("OvenActivity", "temperaturaSpinner: $temperaturaSpinner, programmaSpinner:$programmaSpinner")

        val temperaturaForno = listOf("60", "80", "100", "120", "140", "160", "180", "200", "220", "240")
        val adapter_temp = ArrayAdapter(this, android.R.layout.simple_spinner_item, temperaturaForno)
        adapter_temp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        temperaturaSpinner.adapter = adapter_temp

        val programmaForno = listOf("Statico", "Ventilato", "Grill", "Grill Ventilato", "Resistenza inferiore", "Resistenza superiore")
        val adapter_programma= ArrayAdapter(this, android.R.layout.simple_spinner_item, programmaForno)
        adapter_programma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        programmaSpinner.adapter = adapter_programma

        // Inizializzazione del riferimento al database Firestore
        db = FirebaseFirestore.getInstance()

        val saveButton = findViewById<Button>(R.id.set_forno)
        saveButton.setOnClickListener {
            val setTemperatura= temperaturaSpinner.selectedItem.toString()
            val setProgramma = programmaSpinner.selectedItem.toString()
            saveSettingsToFirestore(setTemperatura, setProgramma)
        }
    }

    private fun saveSettingsToFirestore(setTemperatura: String, setProgramma: String) {
        val forno = dispositivo.Forno(
            temperatura = setTemperatura,
            programma = setProgramma,
            timestamp = Timestamp.now()
        )

        val dispositivoMap = mutableMapOf<String, Any>()
        dispositivoMap["tipo"] = "Forno"

        val fornoMap = mutableMapOf<String, Any>()
        fornoMap["temperatura"] = forno.temperatura
        fornoMap["programma"] = forno.programma
        fornoMap["timestamp"] = forno.timestamp

        dispositivoMap["forno"] = fornoMap

        db.collection("dispositivi")
            .add(dispositivoMap)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Impostazioni salvate", Toast.LENGTH_SHORT).show()
                    updateImpostazioniForno(setTemperatura, setProgramma)
                    val resultIntent = Intent()
                    setResult(Activity.RESULT_OK, resultIntent)
                } else {
                    Toast.makeText(this, "Errore nel salvataggio delle impostazioni", Toast.LENGTH_SHORT).show()
                    Log.d("OvenActivity", "Errore nel salvataggio delle impostazioni: ${task.exception?.message}")
                }
            }
    }
    fun updateImpostazioniForno(setTemperatura: String, setProgramma: String){
        val settingsText = "Forno è impostata con $setTemperatura° in modalità $setProgramma."
        impostazioni_forno.text = settingsText
    }


}
