package com.example.domotik.ui.homeappliances

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domotik.R
import com.example.domotik.ui.dataModel.dispositivo
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class TvActivity : AppCompatActivity() {

    private lateinit var editTextCanale: EditText
    private lateinit var textViewVolume: TextView
    private lateinit var seekBarVolume: SeekBar
    private lateinit var buttonSave: Button
    private lateinit var impostazioni_televisione: TextView
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private val SPEECH_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.televisione_layout)

        mAuth = FirebaseAuth.getInstance()

        editTextCanale = findViewById(R.id.canale_editext)
        textViewVolume = findViewById(R.id.volume_tv)
        seekBarVolume = findViewById(R.id.volume_seekbar)
        impostazioni_televisione = findViewById(R.id.impostazioni_verifica)
        buttonSave = findViewById(R.id.set_tv)

        db = FirebaseFirestore.getInstance()

        seekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewVolume.text = "Volume: $progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // vuoto
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // vuoto
            }
        })

        buttonSave.setOnClickListener {
            val user = mAuth.currentUser
            if (user != null) {
                val email = user.email ?: "Unknown"
                val canale = editTextCanale.text.toString().toIntOrNull()
                val volume = seekBarVolume.progress

                if (canale != null) {
                    saveSettingsToFirestore(canale, volume, email)
                } else {
                    Toast.makeText(this, "Inserisci un canale valido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val speechButton = findViewById<ImageButton>(R.id.microphone)
        speechButton.setOnClickListener { startTextToSpeech() }
    }

    private fun startTextToSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Parla per impostare la televisione")
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
        var selectedVolume: Int? = null
        var selectedCanale: Int? = null

        // Dividi il comando in parole
        val words = command.split(" ")


        for (i in words.indices) {
            if (words[i].equals("volume", ignoreCase = true) && i + 1 < words.size) {
                selectedVolume = words[i + 1].toIntOrNull()
            }
            if (words[i].equals("canale", ignoreCase = true) && i + 1 < words.size) {
                selectedCanale = words[i + 1].toIntOrNull()
            }
        }


        if (selectedVolume != null) {
            seekBarVolume.progress = selectedVolume
        }

        if (selectedCanale != null) {
            editTextCanale.setText(selectedCanale.toString())
        }

        val user = mAuth.currentUser
        if (user != null) {
            val email = user.email ?: "Unknown"
            if (selectedCanale != null && selectedVolume != null) {
                saveSettingsToFirestore(selectedCanale, selectedVolume, email)
            } else {
                Toast.makeText(this, "Comando vocale non riconosciuto", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun saveSettingsToFirestore(canale: Int, volume: Int, email: String) {
        val televisione = dispositivo.Televisione(
            email = email,
            canale = canale,
            volume = volume,
            timestamp = Timestamp.now()
        )

        val dispositivoMap = mutableMapOf<String, Any>()
        dispositivoMap["tipo"] = "Televisione"

        val televisioneMap = mutableMapOf<String, Any>()
        televisioneMap["canale"] = televisione.canale
        televisioneMap["volume"] = televisione.volume
        televisioneMap["timestamp"] = televisione.timestamp

        dispositivoMap["televisione"] = televisioneMap

        db.collection("dispositivi")
            .add(dispositivoMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Impostazioni salvate", Toast.LENGTH_SHORT).show()
                    updateImpostazioniTelevisione(canale, volume, email)
                } else {
                    Toast.makeText(this, "Errore nel salvataggio delle impostazioni", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun updateImpostazioniTelevisione(canale: Int, volume: Int, email: String) {
        val settingsText = "Televisione è impostata sul canale $canale e volume $volume% dall'utente $email."
        impostazioni_televisione.text = settingsText
    }
}
