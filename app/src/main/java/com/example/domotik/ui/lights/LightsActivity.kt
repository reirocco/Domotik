package com.example.domotik.ui.lights

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.domotik.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LightsActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lights)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Lights"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        mAuth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        val cardViewCucina = findViewById<CardView>(R.id.grid_element_cardwiev_cucina)
        cardViewCucina.setOnClickListener {switchOnCucina()}
        val cardViewSala = findViewById<CardView>(R.id.grid_element_cardwiev_sala)
        cardViewSala.setOnClickListener {switchOnSala()}
        val cardViewSoppalco = findViewById<CardView>(R.id.grid_element_cardwiev_soppalco)
        cardViewSoppalco.setOnClickListener {switchOnSoppalco()}
        val cardViewBagno = findViewById<CardView>(R.id.grid_element_cardwiev_bagno)
        cardViewBagno.setOnClickListener {switchOnBagno()}
        val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        val lightsString = sharedPreferences.getString("lights_string", "0_0_0_0")
        val lights = mutableListOf<String>()
        if (lightsString != null) {
            if (lightsString.isNotEmpty()){
                val lightsSplit = lightsString.split("_")
                for (l in lightsSplit) {
                    lights.add(l)
                }
            }
        }
        val imageViewCucina = findViewById<ImageView>(R.id.grid_element_image_cucina)
        if(lights[0].toInt() == 1){
            imageViewCucina.setImageResource(R.drawable.baseline_light_mode_24)
            cardViewCucina.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
            } else {
            imageViewCucina.setImageResource(R.drawable.baseline_emoji_objects_24)
            cardViewCucina.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
        val imageViewSala = findViewById<ImageView>(R.id.grid_element_image_sala)
        if(lights[1].toInt() == 1){
            imageViewSala.setImageResource(R.drawable.baseline_light_mode_24)
            cardViewSala.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))

            } else {
            imageViewSala.setImageResource(R.drawable.baseline_emoji_objects_24)
            cardViewSala.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
        val imageViewSoppalco = findViewById<ImageView>(R.id.grid_element_image_soppalco)
        if(lights[2].toInt() == 1) {
            imageViewSoppalco.setImageResource(R.drawable.baseline_light_mode_24)
            cardViewSoppalco.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.teal_200
                )
            )

            } else {
                imageViewSoppalco.setImageResource(R.drawable.baseline_emoji_objects_24)
                cardViewSoppalco.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.white
                    )
                )
            }
            val imageViewBagno = findViewById<ImageView>(R.id.grid_element_image_bagno)
            if (lights[3].toInt() == 1) {
                imageViewBagno.setImageResource(R.drawable.baseline_light_mode_24)
                cardViewBagno.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.teal_200
                    )
                )
                } else {
                    imageViewBagno.setImageResource(R.drawable.baseline_emoji_objects_24)
                    cardViewBagno.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.white
                        )
                    )
                }
            }

            override fun onSupportNavigateUp(): Boolean {
                finish()
                return true
            }
    private fun saveLightsToDatabase(room: String, state: Boolean) {
        val user = mAuth.currentUser
        val email = user?.email ?: return

        val lightData = hashMapOf(
            "email" to email,
            "room" to room,
            "state" to state,
            "timestamp" to Timestamp.now()
        )

        db.collection("user_logs")
            .add(lightData)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(
                    this,
                    "Log aggiunto correttamente!",
                    Toast.LENGTH_SHORT,
                ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Log fallito!",
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }

    fun switchOnCucina() {
                val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
                val lightsString = sharedPreferences.getString("lights_string", "0_0_0_0")
                val lights = mutableListOf<String>()
                if (lightsString != null) {
                    if (lightsString.isNotEmpty()) {
                        val lightsSplit = lightsString.split("_")
                        for (l in lightsSplit) {
                            lights.add(l)
                        }
                    }
                }
                val light = lights[0].toInt() == 1
                lights[0] = if (!light) "1" else "0"
                with(sharedPreferences.edit()) {
                    putString("lights_string", lights.joinToString("_"))
                    apply()
                }
                val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_cucina)
                val imageView = findViewById<ImageView>(R.id.grid_element_image_cucina)
                val textView = findViewById<TextView>(R.id.grid_element_text_cucina)
                if (!light) {
                    imageView.setImageResource(R.drawable.baseline_light_mode_24)
                    cardView.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.teal_200
                        )
                    )

                } else {
                    imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
                    cardView.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.white
                        )
                    )
                }
                saveLightsToDatabase("Cucina", !light)
            }

            fun switchOnSala() {
                val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
                val lightsString = sharedPreferences.getString("lights_string", "0_0_0_0")
                val lights = mutableListOf<String>()
                if (lightsString != null) {
                    if (lightsString.isNotEmpty()) {
                        val lightsSplit = lightsString.split("_")
                        for (l in lightsSplit) {
                            lights.add(l)
                        }
                    }
                }
                val light = lights[1].toInt() == 1
                lights[1] = if (!light) "1" else "0"
                with(sharedPreferences.edit()) {
                    putString("lights_string", lights.joinToString("_"))
                    apply()
                }
                val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_sala)
                val imageView = findViewById<ImageView>(R.id.grid_element_image_sala)
                val textView = findViewById<TextView>(R.id.grid_element_text_sala)
                if (!light) {
                    imageView.setImageResource(R.drawable.baseline_light_mode_24)
                    cardView.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.teal_200
                        )
                    )
                } else {
                    imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
                    cardView.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.white
                        )
                    )
                }
                saveLightsToDatabase("Sala", !light)
            }

            fun switchOnSoppalco() {
                val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
                val lightsString = sharedPreferences.getString("lights_string", "0_0_0_0")
                val lights = mutableListOf<String>()
                if (lightsString != null) {
                    if (lightsString.isNotEmpty()) {
                        val lightsSplit = lightsString.split("_")
                        for (l in lightsSplit) {
                            lights.add(l)
                        }
                    }
                }
                val light = lights[2].toInt() == 1
                lights[2] = if (!light) "1" else "0"
                with(sharedPreferences.edit()) {
                    putString("lights_string", lights.joinToString("_"))
                    apply()
                }
                val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_soppalco)
                val imageView = findViewById<ImageView>(R.id.grid_element_image_soppalco)
                val textView = findViewById<TextView>(R.id.grid_element_text_soppalco)
                if (!light) {
                    imageView.setImageResource(R.drawable.baseline_light_mode_24)
                    cardView.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.teal_200
                        )
                    )
                } else {
                    imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
                    cardView.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.white
                        )
                    )
                }
                saveLightsToDatabase("Soppalco", !light)
            }

            fun switchOnBagno() {
                val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
                val lightsString = sharedPreferences.getString("lights_string", "0_0_0_0")
                val lights = mutableListOf<String>()
                if (lightsString != null) {
                    if (lightsString.isNotEmpty()) {
                        val lightsSplit = lightsString.split("_")
                        for (l in lightsSplit) {
                            lights.add(l)
                        }
                    }
                }
                val light = lights[3].toInt() == 1
                lights[3] = if (!light) "1" else "0"
                with(sharedPreferences.edit()) {
                    putString("lights_string", lights.joinToString("_"))
                    apply()
                }
                val cardView = findViewById<CardView>(R.id.grid_element_cardwiev_bagno)
                val imageView = findViewById<ImageView>(R.id.grid_element_image_bagno)
                val textView = findViewById<TextView>(R.id.grid_element_text_bagno)
                if (!light) {
                    imageView.setImageResource(R.drawable.baseline_light_mode_24)
                    cardView.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.teal_200
                        )
                    )
                } else {
                    imageView.setImageResource(R.drawable.baseline_emoji_objects_24)
                    cardView.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.white
                        )
                    )
                }
                saveLightsToDatabase("Bagno", !light)
            }
        }