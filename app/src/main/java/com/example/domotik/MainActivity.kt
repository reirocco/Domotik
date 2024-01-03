package com.example.domotik

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.domotik.databinding.ActivityMainBinding
import com.example.domotik.ui.camera.CameraActivity
import com.example.domotik.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

//import com.example.domotik.ui.videosorveglianza.VideoActivity




class MainActivity : AppCompatActivity() {

    // BINDING OBJECT FOR FRAGMENT NAVIGATION
    private lateinit var binding: ActivityMainBinding

    // GLOBAL ATTRIBUTE DEFINITION
    lateinit var cameraCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navView: BottomNavigationView = binding.navView//prova, qui ci va il layout di Rocco
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
        if (FirebaseAuth.getInstance().currentUser != null) {
            binding.navView.selectedItemId = R.id.navigation_dashboard
            binding.navView.setOnItemSelectedListener { menuItem ->

                when (menuItem.itemId) {

                    //R.id.navigation_login -> navController.navigate(R.id.navigation_login)
                    R.id.navigation_dashboard -> navController.navigate(R.id.navigation_login)
                    R.id.navigation_home -> navController.navigate(R.id.navigation_home)
                    R.id.navigation_notifications -> navController.navigate(R.id.impostazioni)


                }
                true
            }
        } else {
            binding.navView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                   R.id.navigation_dashboard -> navController.navigate(R.id.navigation_login)
                    R.id.navigation_home -> navController.navigate(R.id.navigation_home)
                    R.id.navigation_notifications -> navController.navigate(R.id.impostazioni)
                    R.id.impostazioni -> navController.navigate(R.id.navigation_home)

                }
                true
            }
        }
        cameraCard = findViewById<CardView>(R.id.card_camera)
        var cameraCardListener = cameraCard.setOnClickListener {
            Log.v("@string/logHost", "passo")
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

    }

    fun startSecondActivity(view: View) {

            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        fun returnHome(view: View) {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }

    }

