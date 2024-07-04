package com.example.domotik.ui.messaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domotik.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class MessagingActivity : AppCompatActivity() {

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var sendButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Centro Notifiche"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.messagingRecyclerView)
        editText = findViewById(R.id.messagingEditText)
        recyclerView.layoutManager = LinearLayoutManager(this)
        messageAdapter = MessageAdapter(mutableListOf())
        recyclerView.adapter = messageAdapter
        sendButton = findViewById(R.id.messagingSendButton)
        sendButton.setOnClickListener {
            val text = editText.text.toString() //prende il contenuto dell'edit text
            if (text.isNotEmpty()){
                val message = Message(text, Timestamp.now())
                messageAdapter.addMessage(message)
                val messageMap = hashMapOf(
                    "message" to message.message,
                    "timestamp" to message.timestamp
                )
                val user = Firebase.auth.currentUser
                val db = Firebase.firestore
                //db.collection("users").document(user!!.uid).collection("messages").add(messageMap)
                db.collection("users").document(user!!.uid).set(messageMap)
                println(user!!.uid)
                recyclerView.post {
                    recyclerView.layoutManager?.scrollToPosition(messageAdapter.itemCount -1)
                }
                editText.text?.clear() //lo pulisce solo se lo trova non nullo
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}