package com.example.domotik

import android.content.Intent
import android.os.Bundle
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
>>>>>>> Stashed changes
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.domotik.databinding.ActivityMainBinding
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import com.example.domotik.ui.camera.CameraActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    // BINDING OBJECT FOR FRAGMENT NAVIGATION
=======
import com.example.domotik.ui.home.HomeFragment
import com.example.domotik.ui.videosorveglianza.VideoActivity
=======
import com.example.domotik.ui.home.HomeFragment
import com.example.domotik.ui.videosorveglianza.VideoActivity
=======
import com.example.domotik.ui.home.HomeFragment
import com.example.domotik.ui.videosorveglianza.VideoActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    fun startSecondActivity(view: View) {
        val intent = Intent(this, VideoActivity::class.java)
        startActivity(intent)
    }

    fun returnHome(view: View) {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }


    /*private lateinit var binding: ActivityMainBinding
>>>>>>> Stashed changes

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    fun startSecondActivity(view: View) {
        val intent = Intent(this, VideoActivity::class.java)
        startActivity(intent)
    }

    fun returnHome(view: View) {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }


    /*private lateinit var binding: ActivityMainBinding
>>>>>>> Stashed changes

class MainActivity : AppCompatActivity() {
>>>>>>> Stashed changes
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    fun startSecondActivity(view: View) {
        val intent = Intent(this, VideoActivity::class.java)
        startActivity(intent)
    }

    fun returnHome(view: View) {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }


    /*private lateinit var binding: ActivityMainBinding

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
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream



        cameraCard = findViewById<CardView>(R.id.card_camera)
        var cameraCardListener = cameraCard.setOnClickListener {
            Log.v("@string/logHost", "passo")
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }



    }

=======
    }*/
>>>>>>> Stashed changes
=======
    }*/
>>>>>>> Stashed changes
=======
    }*/
>>>>>>> Stashed changes
}