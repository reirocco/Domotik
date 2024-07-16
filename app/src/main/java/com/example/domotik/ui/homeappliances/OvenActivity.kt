package com.example.domotik.ui.homeappliances

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domotik.R
import com.example.domotik.ui.authentication.UserAction
import com.example.domotik.ui.dataModel.dispositivo
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OvenActivity : AppCompatActivity() {
    private lateinit var temperaturaSpinner: Spinner
    private lateinit var programmaSpinner: Spinner
    private lateinit var impostazioni_forno: TextView
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private val SPEECH_REQUEST_CODE = 0
    private lateinit var adapter_temp: ArrayAdapter<String>
    private lateinit var adapter_programma: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forno_layout)

        mAuth = FirebaseAuth.getInstance()

        temperaturaSpinner = findViewById(R.id.temperatura_spinner)
        programmaSpinner = findViewById(R.id.programma_spinner)
        impostazioni_forno = findViewById(R.id.impostazioni_verifica)
        Log.d(
            "OvenActivity",
            "temperaturaSpinner: $temperaturaSpinner, programmaSpinner: $programmaSpinner"
        )

        setupSpinners()

        // Inizializzazione del riferimento al database Firestore
        db = FirebaseFirestore.getInstance()

        val ImageButtonMicrofono = findViewById<ImageButton>(R.id.microphone)
       ImageButtonMicrofono.setOnClickListener {
            startTextToSpeech()
        }

        val saveButton = findViewById<Button>(R.id.set_forno)
        saveButton.setOnClickListener {
            val user = mAuth.currentUser
            if (user != null) {
                val email = user.email ?: "Unknown"
                val setTemperatura = temperaturaSpinner.selectedItem.toString()
                val setProgramma = programmaSpinner.selectedItem.toString()
                saveSettingsToFirestore(email, setTemperatura, setProgramma)
            }
        }
    }

    private fun setupSpinners() {
        val temperaturaForno = listOf("60", "80", "100", "120", "140", "160", "180", "200", "220", "240")
        adapter_temp = ArrayAdapter(this, android.R.layout.simple_spinner_item, temperaturaForno)
        adapter_temp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        temperaturaSpinner.adapter = adapter_temp

        val programmaForno = listOf(
            "Statico",
            "Ventilato",
            "Grill",
            "Grill Ventilato",
            "Resistenza inferiore",
            "Resistenza superiore"
        )
        adapter_programma = ArrayAdapter(this, android.R.layout.simple_spinner_item, programmaForno)
        adapter_programma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        programmaSpinner.adapter = adapter_programma
    }

    private fun saveSettingsToFirestore(
        email: String,
        setTemperatura: String,
        setProgramma: String
    ) {
        val forno = dispositivo.Forno(
            email = email,
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
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Impostazioni salvate", Toast.LENGTH_SHORT).show()
                    updateImpostazioniForno(setTemperatura, setProgramma, email)

                    val details = mapOf(
                        "temperatura" to setTemperatura,
                        "programma" to setProgramma,
                        "email" to email
                    )
                    UserAction.log(email, "imposta_forno", details)

                    val resultIntent = Intent()
                    setResult(Activity.RESULT_OK, resultIntent)
                } else {
                    Toast.makeText(
                        this,
                        "Errore nel salvataggio delle impostazioni",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(
                        "OvenActivity",
                        "Errore nel salvataggio delle impostazioni: ${task.exception?.message}"
                    )
                }
            }
    }

    private fun updateImpostazioniForno(setTemperatura: String, setProgramma: String, email: String) {
        val settingsText =
            "Forno è impostato con $setTemperatura° in modalità $setProgramma dall'utente $email."
        impostazioni_forno.text = settingsText
    }

    private fun startTextToSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Parla per impostare il forno")
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!results.isNullOrEmpty()) {
                val spokenText = results[0]
                gestioneComandiVocali(spokenText)
            }
        }
    }

    private fun gestioneComandiVocali(spokenText: String) {
        val programma_forno = arrayOf(
            "Statico",
            "Ventilato",
            "Grill",
            "Grill ventilato",
            "Resistenza inferiore",
            "Resistenza superiore"
        )
        val temperatura_forno =
            arrayOf("60", "80", "100", "120", "140", "160", "180", "200", "220", "240")

        var selectedProgramma: String? = null
        var selectedTemperatura: String? = null

        for (programma in programma_forno) {
            if (spokenText.contains(programma, ignoreCase = true)) {
                selectedProgramma = programma
                break
            }
        }

        for (temp in temperatura_forno) {
            if (spokenText.contains(temp, ignoreCase = true)) {
                selectedTemperatura = temp
                break
            }
        }

        if (selectedProgramma != null && selectedTemperatura != null) {
            programmaSpinner.setSelection(adapter_programma.getPosition(selectedProgramma))
            temperaturaSpinner.setSelection(adapter_temp.getPosition(selectedTemperatura))

            val user = mAuth.currentUser
            if (user != null) {
                val email = user.email ?: "Unknown"
                saveSettingsToFirestore(email, selectedTemperatura, selectedProgramma)
            }
        } else {
            Toast.makeText(this, "Comando vocale non riconosciuto", Toast.LENGTH_SHORT).show()
        }
    }
}
