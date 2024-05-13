package com.example.domotik

//import com.example.domotik.ui.camera.CameraActivity
//import com.example.domotik.ui.settings.ProfiloUtente
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.domotik.databinding.ActivityMainBinding
import com.example.domotik.ui.Weather.WeatherActivity
import com.example.domotik.ui.camera.CameraActivity
import com.example.domotik.ui.home.HomeFragment
import com.example.domotik.ui.lights.LightsActivity
import com.example.domotik.ui.messaging.MessagingActivity
import com.example.domotik.ui.notifications.NotificationsFragment
import com.example.domotik.ui.settings.ImpostazioniMenu
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
                R.id.navigation_home, R.id.navigation_dashboard, R.id.configuration
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        if (FirebaseAuth.getInstance().currentUser != null) {
            true
        } else {
            true
        }
        //@valeria cannone A CHE SERVE STO CONTROLLO SE FALLISCE? COSA VOLEVI FARE?


        binding.navView.selectedItemId = R.id.navigation_dashboard
        binding.navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                /*
                R.id.navigation_dashboard -> navController.navigate(R.id.impostazioni)
                R.id.navigation_home -> navController.navigate(R.id.navigation_home)
                R.id.configuration -> navController.navigate(R.id.impostazioni)
                R.id.impostazioni -> navController.navigate(R.id.navigation_home)
                R.id.navigation_register -> navController.navigate(R.id.navigation_home)
                R.id.configuration -> navController.navigate(R.id.navigation_dashboard)
                R.id.navigation_login -> navController.navigate(R.id.navigation_login)
                 */

                R.id.navigation_dashboard -> navController.navigate(R.id.impostazioni)
                R.id.navigation_home -> navController.navigate(R.id.navigation_home)
               // R.id.configuration -> navController.navigate(R.id.navigation_configuration)
                R.id.configuration-> navController.navigate(R.id.impostazioni_menu)


            }
            true
        }

        /*cameraCard = findViewById<CardView>(R.id.card_camera)
        var cameraCardListener = cameraCard.setOnClickListener {
            Log.v("@string/logHost", "passo")
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }*/


    }

    fun startSecondActivity(view: View) {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

    fun startLightsActivity(view: View) {
        val intent = Intent(this, LightsActivity::class.java)
        startActivity(intent)
    }

    fun startWeathersActivity(view: View) {
        val intent = Intent(this, WeatherActivity::class.java)
        startActivity(intent)
    }

    fun returnHome(view: View) {
        val intent = Intent(this, HomeFragment::class.java)
        startActivity(intent)
    }
    fun startConfiguration(view: View){
        val intent = Intent(this, NotificationsFragment::class.java)
        startActivity(intent)
    }
    fun showImpostazioniMenu(view: View){
        val intent = Intent(this, ImpostazioniMenu::class.java)
        startActivity(intent)
    }


    /*fun modificaProfilo(view: View){
        val intent = Intent(this, ProfiloUtente::class.java)
        startActivity(intent)
    }*/ //classe da caricare
    fun startMessagingActivity(view: View) {
        val intent = Intent(this, MessagingActivity::class.java)
        startActivity(intent)
    }

}

