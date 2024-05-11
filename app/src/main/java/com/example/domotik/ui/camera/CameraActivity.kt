package com.example.domotik.ui.camera

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.domotik.R

class CameraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

       /* val videoView = findViewById<VideoView>(R.id.videoView)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.lounge2
        videoView.setVideoURI(Uri.parse(videoPath))
        videoView.start()*/
        // Determina se il video è orizzontale o verticale
        val isHorizontalVideo = true // Aggiungi qui la logica per determinare se il video è orizzontale

// Configura l'orientamento dell'Activity in base all'orientamento del video
            requestedOrientation = if (isHorizontalVideo) {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            }

        // Avvia la riproduzione del video
        val videoView = findViewById<VideoView>(R.id.videoView)
        //val videoPath = "android.resource://" + packageName + "/" + R.raw.lounge2
       // videoView.setVideoURI(Uri.parse(videoPath))
       // videoView.start() //caricare il video

    }
        /* requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        // Rileva l'orientamento del dispositivo e regola l'orientamento dell'Activity
        val orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                val rotation = windowManager.defaultDisplay.rotation
                when (rotation) {
                    Surface.ROTATION_0 -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    Surface.ROTATION_90 -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    Surface.ROTATION_180 -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    Surface.ROTATION_270 -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                }
            }
        }
        orientationEventListener.enable()*/

        //GIRA LO SCHERMO MA NON IL VIDEO
        /*val orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) return

                val rotation = when {
                    orientation >= 315 || orientation < 45 -> Surface.ROTATION_0
                    orientation in 45..135 -> Surface.ROTATION_90
                    orientation in 135..225 -> Surface.ROTATION_180
                    else -> Surface.ROTATION_270
                }

                // Ruota il video in base all'orientamento rilevato
                when (rotation) {
                    Surface.ROTATION_0 -> videoView.rotation = 0f
                    Surface.ROTATION_90 -> videoView.rotation = 90f
                    Surface.ROTATION_180 -> videoView.rotation = 180f
                    Surface.ROTATION_270 -> videoView.rotation = 270f
                }
            }
        }
        orientationEventListener.enable()*/



}

