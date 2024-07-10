package com.example.domotik.ui.homeappliances

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.domotik.R

    class HomeAppliancesActivity : AppCompatActivity() {
        companion object {
            const val REQUEST_CODE_LAVATRICE = 1
            const val REQUEST_CODE_FORNO = 1
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_homeappliances)

            val actionbar = supportActionBar
            actionbar?.title = "Dispositivi"
            actionbar?.setDisplayHomeAsUpEnabled(true)

            val cardViewLavatrice = findViewById<CardView>(R.id.grid_element_cardview_lavatrice)
            cardViewLavatrice.setOnClickListener {
                val intent = Intent(this, WashingMachineActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_LAVATRICE)
            }

            val cardViewForno = findViewById<CardView>(R.id.grid_element_cardwiev_forno)
            cardViewForno.setOnClickListener {
                val intent = Intent(this, OvenActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_FORNO)
            }

            val cardViewTv = findViewById<CardView>(R.id.grid_element_cardwiev_televisione)
            cardViewTv.setOnClickListener {
                val intent = Intent(this, TvActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_FORNO)
            }

            val cardViewLavastoviglie = findViewById<CardView>(R.id.grid_element_cardwiev_lavastoviglie)
            cardViewLavastoviglie.setOnClickListener {
                val intent = Intent(this, LavastoviglieActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_LAVATRICE)
            }
            // Altri card view e codice iniziale...

            updateUI()
        }

        override fun onSupportNavigateUp(): Boolean {
            finish()
            return true
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == REQUEST_CODE_LAVATRICE && resultCode == Activity.RESULT_OK) {
                // Puoi aggiornare l'UI in base ai risultati restituiti da LavatriceActivity, se necessario
                updateUI()
            }
        }

        private fun updateUI() {
            val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
            val homeappliances = sharedPreferences.getString("homeappliances_string", "0_0_0_0")
            val dispositivi = homeappliances?.split("_")?.toMutableList() ?: mutableListOf("0", "0", "0", "0")

            val imageViewLavatrice = findViewById<ImageView>(R.id.lavatrice_icon)
            if (dispositivi[0].toInt() == 1) {
                imageViewLavatrice.setImageResource(R.drawable.washing_machine)
                val cardViewLavatrice = findViewById<CardView>(R.id.grid_element_cardview_lavatrice)
                cardViewLavatrice.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.teal_200))
            } else {
                imageViewLavatrice.setImageResource(R.drawable.washing_machine)
                val cardViewLavatrice = findViewById<CardView>(R.id.grid_element_cardview_lavatrice)
                cardViewLavatrice.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
            }

            val imageViewForno = findViewById<ImageView>(R.id.forno_icon)
            if (dispositivi[0].toInt() == 1) {
                imageViewForno.setImageResource(R.drawable.oven)
                val cardViewForno = findViewById<CardView>(R.id.grid_element_cardwiev_forno)
                cardViewForno.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.teal_200))
            } else {
                imageViewForno.setImageResource(R.drawable.oven)
                val cardViewForno = findViewById<CardView>(R.id.grid_element_cardwiev_forno)
                cardViewForno.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
            }


            // Altri dispositivi...
        }
    }
