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
    val devices = mutableListOf<Boolean>(false, false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homeappliances)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Dispositivi"
        // set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        findViewById<CardView>(R.id.grid_element_cardwiev_lavastoviglie).setOnClickListener{switchOnLavastoviglie()}
        findViewById<CardView>(R.id.grid_element_cardview_lavatrice).setOnClickListener{switchOnLavatrice()}
        findViewById<CardView>(R.id.grid_element_cardwiev_forno).setOnClickListener{switchOnForno()}
        findViewById<CardView>(R.id.grid_element_cardwiev_televisione).setOnClickListener{switchOnTelevisione()}

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun switchOnLavastoviglie() {
        val device = devices[0]
        devices[0] = !device
        val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_lavastoviglie)
        val imageView = findViewById<ImageView>(R.id.lavastoviglie_icon)
        val textView = findViewById<TextView>(R.id.lavastoviglie_text)
        if(!device){
            imageView.setImageResource(R.drawable.dishwasher)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.dishwasher)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }

    fun switchOnLavatrice() {
        val device = devices[1]
        devices[1] = !device
        val cardView = findViewById<CardView>(R.id.grid_element_cardview_lavatrice)
        val imageView = findViewById<ImageView>(R.id.lavatrice_icon)
        val textView = findViewById<TextView>(R.id.lavatrice_text)
        if(!device){
            imageView.setImageResource(R.drawable.washing_machine)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.washing_machine)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }

    fun switchOnForno() {
        val device = devices[2]
        devices[2] = !device
        val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_forno)
        val imageView = findViewById<ImageView>(R.id.forno_icon)
        val textView = findViewById<TextView>(R.id.forno_text)
        if(!device){
            imageView.setImageResource(R.drawable.oven)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.oven)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }

    fun switchOnTelevisione() {
        val device = devices[3]
        devices[3] = !device
        val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_televisione)
        val imageView = findViewById<ImageView>(R.id.tv_icon)
        val textView = findViewById<TextView>(R.id.tv_text)
        if(!device){
            imageView.setImageResource(R.drawable.baseline_tv_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.baseline_tv_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }
}

