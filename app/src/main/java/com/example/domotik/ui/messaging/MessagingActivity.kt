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
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

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
        //val messages: MutableList<Message> = mutableListOf();
        messageAdapter = MessageAdapter(mutableListOf())
        recyclerView.adapter = messageAdapter
        val user = Firebase.auth.currentUser
        if (user == null) {
            finish()
            return
        }

        val db = Firebase.firestore
        db.collection("users").document(user.uid).collection("messages").orderBy("timestamp", Query.Direction.ASCENDING).get().addOnSuccessListener { result ->
            for (document in result) {
                val messageData = document.data
                val uid: String = messageData["uid"] as String
                val message: String = messageData["message"] as String
                val timestamp: Timestamp = messageData["timestamp"] as Timestamp
                val messageObject = Message(uid, message, timestamp)
                messageAdapter.addMessage(messageObject)
            }
        }

        sendButton = findViewById(R.id.messagingSendButton)
        sendButton.setOnClickListener {
            val text = editText.text.toString() //prende il contenuto dell'edit text
            if (text.isNotEmpty()){
                val message = Message(user.uid, text, Timestamp.now())
                messageAdapter.addMessage(message)
                val messageMap = hashMapOf(
                    "uid" to message.uid,
                    "message" to message.message,
                    "timestamp" to message.timestamp
                )
                db.collection("users").document(user.uid).collection("messages").add(messageMap)
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