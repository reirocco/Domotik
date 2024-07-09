package com.example.domotik.ui.messaging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.example.domotik.R
import com.example.domotik.ui.camera.CameraActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlin.random.Random

class ChatSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_selection)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Chat"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val newChatButton = findViewById<Button>(R.id.chat_selection_new)
        val joinChatButton = findViewById<Button>(R.id.chat_selection_join)
        newChatButton.setOnClickListener {
            createNewChat()
        }
    }

    private fun createNewChat() {
        val db = Firebase.firestore
        val randomId = Random.nextInt(10000000, 100000000)
        val chatDoc = db.collection("chats").document(randomId.toString())
        chatDoc.get().addOnSuccessListener { result ->
            if (!result.exists()) {
                val user = Firebase.auth.currentUser
                if (user != null) {
                    val chatMembers = mutableListOf<String>()
                    chatMembers.add(user.uid)
                    val chatMap = hashMapOf(
                        "admin" to user.uid,
                        "members" to chatMembers
                    )
                    chatDoc.set(chatMap).addOnSuccessListener {
                        db.collection("users").document(user.uid).update(mapOf("chat" to randomId.toString())).addOnSuccessListener {
                            val intent = Intent(this, MessagingActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            } else{
                createNewChat()
            }
        }
    }

    private fun joinChat(chatId: String) {
        val db = Firebase.firestore
        val chatDoc = db.collection("chats").document(chatId)
        chatDoc.get().addOnSuccessListener { result ->
           if (result.exists()) {
               val chatMembers: MutableList<String> = result.get("members") as MutableList<String>
               val user = Firebase.auth.currentUser
               if (user != null) {
                   chatMembers.add(user.uid)
                   chatDoc.update(mapOf("members" to chatMembers)).addOnSuccessListener {
                       db.collection("users").document(user.uid).update(mapOf("chat" to chatId)).addOnSuccessListener {
                           val intent = Intent(this, MessagingActivity::class.java)
                           startActivity(intent)
                       }
                   }
               }
           } else{
               Toast.makeText(this, "Non esiste una chat con l'Id specificato", Toast.LENGTH_LONG).show()
           }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}