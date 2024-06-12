package com.example.domotik.ui.lights

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.example.domotik.R

class LightsActivity : AppCompatActivity() {
    val lights = mutableListOf<Boolean>(false,false,false,false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lights)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Lights"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        findViewById<CardView>(R.id.grid_element_cardwiev_cucina).setOnClickListener {switchOnCucina()}
        findViewById<CardView>(R.id.grid_element_cardwiev_sala).setOnClickListener {switchOnSala()}
        findViewById<CardView>(R.id.grid_element_cardwiev_soppalco).setOnClickListener {switchOnSoppalco()}
        findViewById<CardView>(R.id.grid_element_cardwiev_bagno).setOnClickListener {switchOnBagno()}

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun switchOnCucina() {
        val light = lights[0]
        lights[0] = !light
        val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_cucina)
        val imageView = findViewById<ImageView>(R.id.grid_element_image_cucina)
        val textView = findViewById<TextView>(R.id.grid_element_text_cucina)
        if(!light){
            imageView.setImageResource(R.drawable.baseline_light_mode_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }

    fun switchOnSala() {
        val light = lights[1]
        lights[1] = !light
        val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_sala)
        val imageView = findViewById<ImageView>(R.id.grid_element_image_sala)
        val textView = findViewById<TextView>(R.id.grid_element_text_sala)
        if(!light){
            imageView.setImageResource(R.drawable.baseline_light_mode_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }

    fun switchOnSoppalco() {
        val light = lights[2]
        lights[2] = !light
        val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_soppalco)
        val imageView = findViewById<ImageView>(R.id.grid_element_image_soppalco)
        val textView = findViewById<TextView>(R.id.grid_element_text_soppalco)
        if(!light){
            imageView.setImageResource(R.drawable.baseline_light_mode_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }

    fun switchOnBagno() {
        val light = lights[3]
        lights[3] = !light
        val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_bagno)
        val imageView = findViewById<ImageView>(R.id.grid_element_image_bagno)
        val textView = findViewById<TextView>(R.id.grid_element_text_bagno)
        if(!light){
            imageView.setImageResource(R.drawable.baseline_light_mode_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
        } else {
            imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
            cardView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }
}
