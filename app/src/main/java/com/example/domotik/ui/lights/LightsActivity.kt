package com.example.domotik.ui.lights

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
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
        val imageView = findViewById<ImageView>(R.id.grid_element_image_cucina)
        val textView = findViewById<TextView>(R.id.grid_element_text_cucina)
        if(!light){
            imageView.setImageResource(R.drawable.baseline_light_mode_24)
        } else {
            imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
        }
    }

    fun switchOnSala() {
        val light = lights[1]
        lights[1] = !light
        val imageView = findViewById<ImageView>(R.id.grid_element_image_sala)
        val textView = findViewById<TextView>(R.id.grid_element_text_sala)
        if(!light){
            imageView.setImageResource(R.drawable.baseline_light_mode_24)
        } else {
            imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
        }
    }

    fun switchOnSoppalco() {
        val light = lights[2]
        lights[2] = !light
        val imageView = findViewById<ImageView>(R.id.grid_element_image_soppalco)
        val textView = findViewById<TextView>(R.id.grid_element_text_soppalco)
        if(!light){
            imageView.setImageResource(R.drawable.baseline_light_mode_24)
        } else {
            imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
        }
    }

    fun switchOnBagno() {
        val light = lights[3]
        lights[3] = !light
        val imageView = findViewById<ImageView>(R.id.grid_element_image_bagno)
        val textView = findViewById<TextView>(R.id.grid_element_text_bagno)
        if(!light){
            imageView.setImageResource(R.drawable.baseline_light_mode_24)
        } else {
            imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
        }
    }
}
