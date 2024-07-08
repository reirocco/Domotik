package com.example.domotik.ui.messaging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.domotik.R
import com.example.domotik.ui.camera.CameraActivity

class ChatSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_selection)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Chat"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.chat_info) {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}