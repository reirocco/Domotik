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
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domotik.R
import com.example.domotik.ui.authentication.UserAction
import com.example.domotik.ui.dataModel.dispositivo
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class LavastoviglieActivity : AppCompatActivity() {

    private lateinit var saleSwitch: Switch
    private lateinit var pastiglieSwitch: Switch
    private lateinit var modalitaSpinner: Spinner
    private lateinit var impostazioni_lavastoviglie: TextView
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private val SPEECH_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lavastoviglie_layout)

        mAuth = FirebaseAuth.getInstance()

        val speechButton = findViewById<ImageButton>(R.id.microphone)
        speechButton.setOnClickListener { startTextToSpeech() }

        saleSwitch = findViewById(R.id.sale_switch)
        pastiglieSwitch = findViewById(R.id.pastiglie_switch)
        modalitaSpinner = findViewById(R.id.modalità_spinner)
        impostazioni_lavastoviglie = findViewById(R.id.impostazioni_verifica)
        Log.d(
            "LavastoviglieActivity",
            "saleSwitch: $saleSwitch, pastiglieSwitch: $pastiglieSwitch, modalitaSpinner: $modalitaSpinner"
        )

        val modalitaLavastoviglie = listOf("Auto", "Eco", "Programma Rapido", "Programma Intensivo", "Programma Bicchieri")
        val adapterMode = ArrayAdapter(this, android.R.layout.simple_spinner_item, modalitaLavastoviglie)
        adapterMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        modalitaSpinner.adapter = adapterMode

        // Inizializzazione del riferimento al database Firestore
        db = FirebaseFirestore.getInstance()

        val saveButton = findViewById<Button>(R.id.set_lavastoviglie)
        saveButton.setOnClickListener {
            val user = mAuth.currentUser
            if (user != null) {
                val email = user.email ?: "Unknown"
                val setSale = if (saleSwitch.isChecked) "Sale inserito" else "Sale non inserito"
                val setPastiglie = if (pastiglieSwitch.isChecked) "Pastiglie inserite" else "Pastiglie non inserite"
                val setModalita = modalitaSpinner.selectedItem.toString()

                if (!pastiglieSwitch.isChecked) {
                    Toast.makeText(
                        this,
                        "Pastiglia non inserita, impossibile effettuare lavaggio",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    saveSettingsToFirestore(email, setSale, setPastiglie, setModalita)
                }
            }
        }
    }

    private fun saveSettingsToFirestore(
        email: String,
        setSale: String,
        setPastiglie: String,
        setModalita: String
    ) {
        val lavastoviglie = dispositivo.Lavastoviglie(
            email = email,
            sale = setSale,
            pastiglie = setPastiglie,
            modalita = setModalita,
            timestamp = Timestamp.now()
        )

        val dispositivoMap = mutableMapOf<String, Any>()
        dispositivoMap["tipo"] = "Lavastoviglie"

        val lavastoviglieMap = mutableMapOf<String, Any>()
        lavastoviglieMap["sale"] = lavastoviglie.sale
        lavastoviglieMap["pastiglie"] = lavastoviglie.pastiglie
        lavastoviglieMap["modalita"] = lavastoviglie.modalita
        lavastoviglieMap["timestamp"] = lavastoviglie.timestamp

        dispositivoMap["lavastoviglie"] = lavastoviglieMap

        db.collection("dispositivi")
            .add(dispositivoMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Impostazioni salvate", Toast.LENGTH_SHORT).show()
                    updateImpostazioniLavastoviglie(setSale, setPastiglie, setModalita, email)

                    val details = mapOf(
                        "sale" to setSale,
                        "pastiglie" to setPastiglie,
                        "modalità" to setModalita,
                        "email" to email
                    )
                    UserAction.log(email, "imposta_lavastoviglie", details)

                    val resultIntent = Intent()
                    setResult(Activity.RESULT_OK, resultIntent)
                } else {
                    Toast.makeText(
                        this,
                        "Errore nel salvataggio delle impostazioni",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(
                        "LavastoviglieActivity",
                        "Errore nel salvataggio delle impostazioni: ${task.exception?.message}"
                    )
                }
            }
    }

    private fun updateImpostazioniLavastoviglie(
        setSale: String,
        setPastiglie: String,
        setModalita: String,
        email: String
    ) {
        val settingsText = "Lavastoviglie è impostata con $setSale e $setPastiglie in modalità $setModalita dall'utente $email."
        impostazioni_lavastoviglie.text = settingsText
    }

    private fun startTextToSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Parla per impostare la lavastoviglie")
        }

        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "I comandi vocali non sono supportati su questo dispositivo",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (results != null && results.isNotEmpty()) {
                gestioneComandiVocali(results[0])
            }
        }
    }

    private fun gestioneComandiVocali(command: String) {
        val email = mAuth.currentUser?.email ?: return

        var setSale: String? = null
        var setPastiglie: String? = null
        var setModalita: String? = null

        if (command.contains("sale", ignoreCase = true)) {
            saleSwitch.isChecked = !saleSwitch.isChecked
            setSale = if (saleSwitch.isChecked) "Sale inserito" else "Sale non inserito"
        } else {
            setSale = if (saleSwitch.isChecked) "Sale inserito" else "Sale non inserito"
        }

        if (command.contains("pastiglie", ignoreCase = true)) {
            pastiglieSwitch.isChecked = !pastiglieSwitch.isChecked
            setPastiglie = if (pastiglieSwitch.isChecked) "Pastiglie inserite" else "Pastiglie non inserite"
        }

        val modalitaLavastoviglie = listOf("Auto", "Eco", "Programma Rapido", "Programma Intensivo", "Programma Bicchieri")
        for (mode in modalitaLavastoviglie) {
            if (command.contains(mode.toLowerCase(Locale.ROOT))) {
                modalitaSpinner.setSelection(modalitaLavastoviglie.indexOf(mode))
                setModalita = mode
                break
            }
        }

        if (setPastiglie != null && setModalita != null) {
            saveSettingsToFirestore(email, setSale, setPastiglie, setModalita)
        } else {
            Toast.makeText(this, "Comando vocale non completo o non riconosciuto", Toast.LENGTH_SHORT).show()
        }
    }
}
