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

class WashingMachineActivity : AppCompatActivity() {
    private lateinit var gradiSpinner: Spinner
    private lateinit var modalitaSpinner: Spinner
    private lateinit var impostazioni_lavatrice: TextView
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private val SPEECH_REQUEST_CODE = 0
    private lateinit var adapter_gradi: ArrayAdapter<String>
    private lateinit var adapter_modalita: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lavatrice_layout)

        mAuth = FirebaseAuth.getInstance()

        gradiSpinner = findViewById(R.id.gradi_spinner)
        modalitaSpinner = findViewById(R.id.modalità_spinner)
        impostazioni_lavatrice = findViewById(R.id.impostazioni_verifica)
        Log.d("WashingMachineActivity", "gradiSpinner: $gradiSpinner, modalitaSpinner: $modalitaSpinner")

        setupSpinners()

        /*val gradiLavatrice = listOf("30", "40", "50", "60")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, gradiLavatrice)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gradiSpinner.adapter = adapter1

        val modalitaLavatrice = listOf("Delicati", "Lana", "Sport", "Cotoni")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, modalitaLavatrice)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        modalitaSpinner.adapter = adapter2*/

        // Inizializzazione del riferimento al database Firestore
        db = FirebaseFirestore.getInstance()

        val ImageButtonMicrofono = findViewById<ImageButton>(R.id.microphone)
        ImageButtonMicrofono.setOnClickListener {
            startTextToSpeech()
        }

        val saveButton = findViewById<Button>(R.id.set_lavatrice)
        saveButton.setOnClickListener {
            val user = mAuth.currentUser
            if (user != null) {
                val email = user.email ?: "Unknown"
                val setGradi = gradiSpinner.selectedItem.toString()
                val setModalita = modalitaSpinner.selectedItem.toString()
                saveSettingsToFirestore(setGradi, setModalita, email)
            }
        }
    }
    private fun setupSpinners() {
        val gradiLavatrice = arrayOf("30", "40", "50", "60")
        adapter_gradi = ArrayAdapter(this, android.R.layout.simple_spinner_item, gradiLavatrice)
        adapter_gradi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gradiSpinner.adapter = adapter_gradi

        val modalitaLavatrice = arrayOf("Delicati", "Lana", "Sport", "Cotoni")
        adapter_modalita = ArrayAdapter(this, android.R.layout.simple_spinner_item, modalitaLavatrice)
        adapter_modalita.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        modalitaSpinner.adapter = adapter_modalita
    }

    private fun saveSettingsToFirestore(setGradi: String, setModalita: String, email: String) {
        val lavatrice = dispositivo.Lavatrice(
            email = email,
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
                       updateImpostazioniLavatrice(setGradi, setModalita, email)

                    val details = mapOf(
                        "gradi" to setGradi,
                        "modalita" to setModalita,
                        "email" to email
                    )
                    UserAction.log(email, "imposta_lavatrice", details)


                    val resultIntent = Intent()
                    setResult(Activity.RESULT_OK, resultIntent)
                } else {
                    Toast.makeText(this, "Errore nel salvataggio delle impostazioni", Toast.LENGTH_SHORT).show()
                    Log.d("WashingMachineActivity", "Errore nel salvataggio delle impostazioni: ${task.exception?.message}")
                }
            }
    }
    private fun updateImpostazioniLavatrice(setGradi: String, setModalita: String, email: String) {
        val settingsText = "Lavatrice è impostata con $setGradi° in modalità $setModalita dall'utente $email."
        impostazioni_lavatrice.text = settingsText
    }

    private fun startTextToSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Parla per impostare la lavatrice")
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
        val modalita_lavatrice = arrayOf(
            "Delicati", "Lana", "Sport", "Cotoni"
        )
        val gradi_lavatrice =
           arrayOf("30", "40", "50", "60")

        var selectedGradi: String? = null
        var selectedModalita: String? = null

        for (programma in modalita_lavatrice) {
            if (spokenText.contains(programma, ignoreCase = true)) {
                selectedModalita = programma
                break
            }
        }

        for (temp in gradi_lavatrice) {
            if (spokenText.contains(temp, ignoreCase = true)) {
                selectedGradi = temp
                break
            }
        }

        if (selectedModalita != null && selectedGradi != null) {
            modalitaSpinner.setSelection(adapter_modalita.getPosition(selectedModalita))
            gradiSpinner.setSelection(adapter_gradi.getPosition(selectedGradi))

            val user = mAuth.currentUser
            if (user != null) {
                val email = user.email ?: "Unknown"
                saveSettingsToFirestore(selectedGradi, selectedModalita, email)
            }
        } else {
            Toast.makeText(this, "Comando vocale non riconosciuto", Toast.LENGTH_SHORT).show()
        }
    }
}
