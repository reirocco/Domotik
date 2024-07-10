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

class WashingMachineActivity : AppCompatActivity() {
    private lateinit var gradiSpinner: Spinner
    private lateinit var modalitaSpinner: Spinner
    private lateinit var impostazioni_lavatrice: TextView
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lavatrice_layout)

        gradiSpinner = findViewById(R.id.gradi_spinner)
        modalitaSpinner = findViewById(R.id.modalità_spinner)
        impostazioni_lavatrice = findViewById(R.id.impostazioni_verifica)
        Log.d("WashingMachineActivity", "gradiSpinner: $gradiSpinner, modalitaSpinner: $modalitaSpinner")

        val gradiLavatrice = listOf("30", "40", "50", "60")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, gradiLavatrice)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gradiSpinner.adapter = adapter1

        val modalitaLavatrice = listOf("Delicati", "Lana", "Sport", "Cotoni")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, modalitaLavatrice)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        modalitaSpinner.adapter = adapter2

        // Inizializzazione del riferimento al database Firestore
        db = FirebaseFirestore.getInstance()

        val saveButton = findViewById<Button>(R.id.set_lavatrice)
        saveButton.setOnClickListener {
            val setGradi = gradiSpinner.selectedItem.toString()
            val setModalita = modalitaSpinner.selectedItem.toString()
            saveSettingsToFirestore(setGradi, setModalita)

        }
    }

    private fun saveSettingsToFirestore(setGradi: String, setModalita: String) {
        val lavatrice = dispositivo.Lavatrice(
            gradi = setGradi,
            modalita = setModalita,
            timestamp = Timestamp.now()
        )

        val dispositivoMap = mutableMapOf<String, Any>()
        dispositivoMap["tipo"] = "Lavatrice"

        val lavatriceMap = mutableMapOf<String, Any>()
        lavatriceMap["gradi"] = lavatrice.gradi
        lavatriceMap["modalita"] = lavatrice.modalita
        lavatriceMap["timestamp"] = lavatrice.timestamp

        dispositivoMap["lavatrice"] = lavatriceMap

        db.collection("dispositivi")
            .add(dispositivoMap)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Impostazioni salvate", Toast.LENGTH_SHORT).show()
                       updateImpostazioniLavatrice(setGradi, setModalita)
                    val resultIntent = Intent()
                    setResult(Activity.RESULT_OK, resultIntent)
                } else {
                    Toast.makeText(this, "Errore nel salvataggio delle impostazioni", Toast.LENGTH_SHORT).show()
                    Log.d("WashingMachineActivity", "Errore nel salvataggio delle impostazioni: ${task.exception?.message}")
                }
            }
    }
    private fun updateImpostazioniLavatrice(setGradi: String, setModalita: String) {
        val settingsText = "Lavatrice è impostata con $setGradi° in modalità $setModalita."
        impostazioni_lavatrice.text = settingsText
    }
}
