package com.example.domotik

//import com.example.domotik.ui.camera.CameraActivity
//import com.example.domotik.ui.settings.ProfiloUtente
import Worker
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import com.example.domotik.databinding.ActivityMainBinding
import com.example.domotik.ui.Weather.IndoorWeatherActivity
import com.example.domotik.ui.Weather.WeatherActivity
import com.example.domotik.ui.camera.CameraActivity
import com.example.domotik.ui.home.HomeFragment
import com.example.domotik.ui.homeappliances.HomeAppliancesActivity
import com.example.domotik.ui.homeappliances.LavastoviglieActivity
import com.example.domotik.ui.homeappliances.OvenActivity
import com.example.domotik.ui.homeappliances.TvActivity
import com.example.domotik.ui.homeappliances.WashingMachineActivity
import com.example.domotik.ui.lights.LightsActivity
import com.example.domotik.ui.messaging.MessagingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    // BINDING OBJECT FOR FRAGMENT NAVIGATION
    private lateinit var binding: ActivityMainBinding

    // GLOBAL ATTRIBUTE DEFINITION
    lateinit var cameraCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pianificazione del worker in background ogni 15 minuti
        schedulePeriodicWork(this)

        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_message
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.navView.selectedItemId = R.id.navigation_dashboard
        binding.navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.navigation_dashboard -> navController.navigate(R.id.impostazioni)
                R.id.navigation_home -> navController.navigate(R.id.navigation_home)
                R.id.navigation_message-> {
                    val user = Firebase.auth.currentUser
                    if (user != null) {
                        val db = Firebase.firestore
                        db.collection("users").document(user.uid).get().addOnSuccessListener { result->
                            if (result.data?.containsKey("chat") == true) {
                                navController.navigate(R.id.chat_activity)
                            } else {
                                navController.navigate(R.id.chat_selection_activity)
                            }
                        }
                    }
                }
            }
            true
        }
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
        val intent = Intent(this, IndoorWeatherActivity::class.java)
        startActivity(intent)
    }

    fun returnHome(view: View) {
        val intent = Intent(this, HomeFragment::class.java)
        startActivity(intent)
    }

    fun startMessagingActivity(view: View) {
        val intent = Intent(this, MessagingActivity::class.java)
        startActivity(intent)
    }

    fun startWashingMachineActivity(view: View){
        val intent = Intent(this, WashingMachineActivity::class.java)
        startActivity(intent)
    }

    fun startHomeAppliancesActivity(view: View) {
        val intent = Intent(this, HomeAppliancesActivity::class.java)
        startActivity(intent)
    }
    fun startOvenActivity(view: View){
        val intent = Intent(this, OvenActivity::class.java)
        startActivity(intent)
    }
    fun startTvActivity(view: View) {
        val intent = Intent(this, TvActivity::class.java)
        startActivity(intent)
    }

    fun startLavastoviglieActivity(view: View) {
        val intent = Intent(this, LavastoviglieActivity::class.java)
        startActivity(intent)
    }

    fun schedulePeriodicWork(context: Context) {
        val periodicWorkRequest = PeriodicWorkRequestBuilder<Worker>(15, TimeUnit.MINUTES)
            .setInitialDelay(0, TimeUnit.MINUTES) // Optional: set initial delay
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "timeAnalisysWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )
    }
}

