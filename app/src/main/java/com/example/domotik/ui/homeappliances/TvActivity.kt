package com.example.domotik.ui.homeappliances

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domotik.R
import com.example.domotik.ui.dataModel.dispositivo
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class TvActivity : AppCompatActivity() {

    private lateinit var editTextCanale: EditText
    private lateinit var textViewVolume: TextView
    private lateinit var seekBarVolume: SeekBar
    private lateinit var buttonSave: Button
    private lateinit var impostazioni_televisione : TextView
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.televisione_layout)

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
                // Do nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }
        })

        buttonSave.setOnClickListener {
            val canale = editTextCanale.text.toString().toIntOrNull()
            val volume = seekBarVolume.progress

            if (canale != null) {
                saveSettingsToFirestore(canale, volume)
            } else {
                Toast.makeText(this, "Inserisci un canale valido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveSettingsToFirestore(canale: Int, volume: Int) {
        val televisione = dispositivo.Televisione(
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
                    updateImpostazioniTelevisione(canale, volume)
                } else {
                    Toast.makeText(this, "Errore nel salvataggio delle impostazioni", Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun updateImpostazioniTelevisione(volume: Int, canale: Int){
        val settingsText = "Televisione è impostata sul canale $canale e volume $volume."
        impostazioni_televisione.text = settingsText
        }

}
