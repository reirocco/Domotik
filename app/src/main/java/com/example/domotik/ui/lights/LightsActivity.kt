package com.example.domotik.ui.lights

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.domotik.R

class LightsActivity : AppCompatActivity() {
    val lights = mutableListOf<Boolean>(false,false,false,false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lights)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Lighs"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

    }
    /* override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } */

    fun SwitchOnCucina1(view: View) {
        val light = lights[0]
        lights[0] = !light
        if(!light){
            findViewById<ImageButton>(R.id.button1).setImageResource(R.drawable.baseline_light_mode_24)
        } else {
            findViewById<ImageButton>(R.id.button1).setImageResource(R.drawable.baseline_emoji_objects_24)
        }
    }

    fun SwitchOnCucina2(view: View) {
        val light = lights[1]
        lights[1] = !light
        if(!light){
            findViewById<ImageButton>(R.id.button2).setImageResource(R.drawable.baseline_light_mode_24)
        } else {
            findViewById<ImageButton>(R.id.button2).setImageResource(R.drawable.baseline_emoji_objects_24)
        }
    }

    fun SwitchOnCucina3(view: View) {
        val light = lights[2]
        lights[2] = !light
        if(!light){
            findViewById<ImageButton>(R.id.button3).setImageResource(R.drawable.baseline_light_mode_24)
        } else {
            findViewById<ImageButton>(R.id.button3).setImageResource(R.drawable.baseline_emoji_objects_24)
        }
    }

    fun SwitchOnCucina4(view: View) {
        val light = lights[3]
        lights[3] = !light
        if(!light){
            findViewById<ImageButton>(R.id.button4).setImageResource(R.drawable.baseline_light_mode_24)
        } else {
            findViewById<ImageButton>(R.id.button4).setImageResource(R.drawable.baseline_emoji_objects_24)
        }
    }
}
