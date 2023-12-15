package com.example.domotik

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.domotik.databinding.ActivityMainBinding
import com.example.domotik.ui.camera.CameraActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    // BINDING OBJECT FOR FRAGMENT NAVIGATION
    private lateinit var binding: ActivityMainBinding

    // GLOBAL ATTRIBUTE DEFINITION
    lateinit var cameraCard : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        cameraCard = findViewById<CardView>(R.id.card_camera)
        var cameraCardListener = cameraCard.setOnClickListener {
            Log.v("@string/logHost", "passo")
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }



    }

}