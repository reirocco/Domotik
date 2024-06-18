package com.example.domotik.ui.homeappliances

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.domotik.R

class HomeAppliancesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homeappliances)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Dispositivi"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        val cardViewLavatrice = findViewById<CardView>(R.id.grid_element_cardview_lavatrice)
        cardViewLavatrice.setOnClickListener {switchOnLavatrice()}
        val cardViewLavastoviglie = findViewById<CardView>(R.id.grid_element_cardwiev_lavastoviglie)
        cardViewLavastoviglie.setOnClickListener {switchOnLavastoviglie()}
        val cardViewForno = findViewById<CardView>(R.id.grid_element_cardwiev_forno)
        cardViewForno.setOnClickListener {switchOnForno()}
        val cardViewTelevisione = findViewById<CardView>(R.id.grid_element_cardwiev_televisione)
        cardViewTelevisione.setOnClickListener {switchOnTelevisione()}
        val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        val homeappliances = sharedPreferences.getString("homeappliances_string", "0_0_0_0")
        val dispositivi = mutableListOf<String>()
        if (homeappliances != null) {
            if (homeappliances.isNotEmpty()){
                val dispositiviSplit = homeappliances.split("_")
                for (d in dispositiviSplit) {
                    dispositivi.add(d)
                }
            }
        }
        val imageViewLavatrice = findViewById<ImageView>(R.id.lavatrice_icon)
        if(dispositivi[0].toInt() == 1){
            imageViewLavatrice.setImageResource(R.drawable.washing_machine)
            cardViewLavatrice.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageViewLavatrice.setImageResource(R.drawable.washing_machine)
            cardViewLavatrice.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
        val imageViewLavastoviglie = findViewById<ImageView>(R.id.lavastoviglie_icon)
        if(dispositivi[1].toInt() == 1){
            imageViewLavastoviglie.setImageResource(R.drawable.dishwasher)
            cardViewLavastoviglie.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageViewLavastoviglie.setImageResource(R.drawable.dishwasher)
            cardViewLavastoviglie.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
        val imageViewForno = findViewById<ImageView>(R.id.forno_icon)
        if(dispositivi[2].toInt() == 1){
            imageViewForno.setImageResource(R.drawable.oven)
            cardViewForno.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageViewForno.setImageResource(R.drawable.oven)
            cardViewForno.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
        val imageViewTelevisione = findViewById<ImageView>(R.id.tv_icon)
        if(dispositivi[3].toInt() == 1){
            imageViewTelevisione.setImageResource(R.drawable.baseline_tv_24)
            cardViewTelevisione.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageViewTelevisione.setImageResource(R.drawable.baseline_tv_24)
            cardViewTelevisione.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun switchOnLavatrice() {
        val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        val homeappliances = sharedPreferences.getString("homeappliances_string", "0_0_0_0")
        val dispositivi = mutableListOf<String>()
        if (homeappliances != null) {
            if (homeappliances.isNotEmpty()){
                val dispositiviSplit = homeappliances.split("_")
                for (d in dispositiviSplit) {
                   dispositivi.add(d)
                }
            }
        }
        val dispositivo = dispositivi[0].toInt() == 1
        dispositivi[0] = if (!dispositivo) "1" else "0"
        with(sharedPreferences.edit()) {
            putString("homeappliances_string", dispositivi.joinToString("_"))
            apply()
        }
        val cardView = findViewById<CardView>(R.id.grid_element_cardview_lavatrice)
        val imageView = findViewById<ImageView>(R.id.lavatrice_icon)
        val textView = findViewById<TextView>(R.id.lavatrice_text)
        if(!dispositivo){
            imageView.setImageResource(R.drawable.washing_machine)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.washing_machine)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }

    fun switchOnLavastoviglie() {
        val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        val homeappliances = sharedPreferences.getString("homeappliances_string", "0_0_0_0")
        val dispositivi = mutableListOf<String>()
        if (homeappliances != null) {
            if (homeappliances.isNotEmpty()){
                val dispositiviSplit = homeappliances.split("_")
                for (d in dispositiviSplit) {
                    dispositivi.add(d)
                }
            }
        }
        val dispositivo = dispositivi[1].toInt() == 1
        dispositivi[1] = if (!dispositivo) "1" else "0"
        with(sharedPreferences.edit()) {
            putString("homeappliances_string", dispositivi.joinToString("_"))
            apply()
        }
        val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_lavastoviglie)
        val imageView = findViewById<ImageView>(R.id.lavastoviglie_icon)
        val textView = findViewById<TextView>(R.id.lavastoviglie_text)
        if(!dispositivo){
            imageView.setImageResource(R.drawable.dishwasher)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.dishwasher)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }

    fun switchOnForno() {
        val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        val homeappliances = sharedPreferences.getString("homeappliances_string", "0_0_0_0")
        val dispositivi = mutableListOf<String>()
        if (homeappliances != null) {
            if (homeappliances.isNotEmpty()){
                val dispositiviSplit = homeappliances.split("_")
                for (d in dispositiviSplit) {
                    dispositivi.add(d)
                }
            }
        }
        val dispositivo = dispositivi[2].toInt() == 1
        dispositivi[2] = if (!dispositivo) "1" else "0"
        with(sharedPreferences.edit()) {
            putString("homeappliances_string", dispositivi.joinToString("_"))
            apply()
        }
        val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_forno)
        val imageView = findViewById<ImageView>(R.id.forno_icon)
        val textView = findViewById<TextView>(R.id.forno_text)
        if(!dispositivo){
            imageView.setImageResource(R.drawable.oven)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.oven)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }

    fun switchOnTelevisione() {
        val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        val homeappliances = sharedPreferences.getString("homeappliances_string", "0_0_0_0")
        val dispositivi = mutableListOf<String>()
        if (homeappliances != null) {
            if (homeappliances.isNotEmpty()){
                val dispositiviSplit = homeappliances.split("_")
                for (d in dispositiviSplit) {
                    dispositivi.add(d)
                }
            }
        }
        val dispositivo = dispositivi[3].toInt() == 1
        dispositivi[3] = if (!dispositivo) "1" else "0"
        with(sharedPreferences.edit()) {
            putString("homeappliances_string", dispositivi.joinToString("_"))
            apply()
        }
        val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_televisione)
        val imageView = findViewById<ImageView>(R.id.tv_icon)
        val textView = findViewById<TextView>(R.id.tv_text)
        if(!dispositivo){
            imageView.setImageResource(R.drawable.baseline_tv_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.baseline_tv_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }
}






