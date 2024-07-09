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
import com.example.domotik.ui.dataModel.dispositivo
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class WashingMachineActivity : AppCompatActivity() {
    private lateinit var gradiSpinner: Spinner
    private lateinit var modalitaSpinner: Spinner
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lavatrice_layout)

        gradiSpinner = findViewById(R.id.gradi_spinner)
        modalitaSpinner = findViewById(R.id.modalità_spinner)
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
            val selectedGradi = gradiSpinner.selectedItem.toString()
            val selectedModalita = modalitaSpinner.selectedItem.toString()
            saveSettingsToFirestore(selectedGradi, selectedModalita)
        }
    }

    private fun saveSettingsToFirestore(selectedGradi: String, selectedModalita: String) {
        val dispositivo = dispositivo(
            gradi = selectedGradi,
            modalita = selectedModalita,
            timestamp = Timestamp.now()
        )

        db.collection("dispositivi").add(dispositivo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Impostazioni salvate", Toast.LENGTH_SHORT).show()
                    val resultIntent = Intent()
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                } else {
                    Toast.makeText(this, "Errore nel salvataggio delle impostazioni", Toast.LENGTH_SHORT).show()
                    Log.d("WashingMachineActivity", "Errore nel salvataggio delle impostazioni: ${task.exception?.message}")
                }
            }
    }
}
