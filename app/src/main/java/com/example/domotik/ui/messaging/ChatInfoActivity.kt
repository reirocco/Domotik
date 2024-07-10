package com.example.domotik.ui.messaging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.domotik.MainActivity
import com.example.domotik.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class ChatInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_info)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Informazioni Chat"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val chatIdText = findViewById<TextView>(R.id.chat_id)
        val leaveChat = findViewById<Button>(R.id.chat_leave)
        val user = Firebase.auth.currentUser
        var chatId: String = ""
        if (user != null) {
            val db = Firebase.firestore
            db.collection("users").document(user.uid).get().addOnSuccessListener {result ->
                chatId = result.getLong("chat").toString()
                chatIdText.text = chatId
            }
        }
        leaveChat.setOnClickListener {
            leaveChat(chatId)
        }
    }

    private fun leaveChat(chatId: String) {
        val user = Firebase.auth.currentUser
        if (user != null) {
            val db = Firebase.firestore
            val chatDoc = db.collection("chats").document(chatId)
            chatDoc.get().addOnSuccessListener { result ->
                if (result.exists()) {
                    val chatMembers: MutableList<String> = result.get("members") as MutableList<String>
                    chatMembers.remove(user.uid)
                    chatDoc.update(mapOf("members" to chatMembers)).addOnSuccessListener {
                        db.collection("users").document(user.uid).update(mapOf("chat" to FieldValue.delete())).addOnSuccessListener {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    }
                } else{
                    Toast.makeText(this, "Non esiste una chat con l'Id specificato", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}