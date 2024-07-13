package com.example.domotik.ui.messaging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domotik.R
import com.example.domotik.ui.camera.CameraActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentChange
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
        val user = Firebase.auth.currentUser
        if (user == null) {
            finish()
            return
        }

        messageAdapter = MessageAdapter(mutableListOf(), user.uid)
        recyclerView.adapter = messageAdapter
        val db = Firebase.firestore
        var userName: String = ""
        var chatId: String = ""
        db.collection("users").document(user.uid).get().addOnSuccessListener { result->
            userName = result.data?.get("username") as String
            chatId = result.data?.get("chat") as String

            db.collection("chats").document(chatId).collection("messages").orderBy("timestamp", Query.Direction.ASCENDING).addSnapshotListener{qs, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (dc in qs!!.documentChanges) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        val messageData = dc.document.data
                        val senderUid: String = messageData["senderUid"] as String
                        val senderUsername: String = messageData["senderUsername"] as String
                        val message: String = messageData["message"] as String
                        val timestamp: Timestamp = messageData["timestamp"] as Timestamp
                        val messageObject = Message(senderUid, senderUsername, message, timestamp)
                        messageAdapter.addMessage(messageObject)
                    }
                }
                recyclerView.post {
                    recyclerView.layoutManager?.scrollToPosition(messageAdapter.itemCount -1)
                }
            }
        }

        sendButton = findViewById(R.id.messagingSendButton)
        sendButton.setOnClickListener {
            val text = editText.text.toString() //prende il contenuto dell'edit text
            if (text.isNotEmpty()){
                val message = Message(user.uid, userName, text, Timestamp.now())
                val messageMap = hashMapOf(
                    "senderUid" to message.senderUid,
                    "senderUsername" to message.senderUsername,
                    "message" to message.message,
                    "timestamp" to message.timestamp
                )
                db.collection("chats").document(chatId).collection("messages").add(messageMap)
                editText.text?.clear() //lo pulisce solo se lo trova non nullo
            }
        }

        /*sendButton.setOnClickListener {
            val text = "Questo è il messaggio di prova di sistema e lo sto facendo per Rocco"
            val message = Message("system_id", "Sistema", text, Timestamp.now())
            messageAdapter.addMessage(message)
            val messageMap = hashMapOf(
                "senderUid" to message.senderUid,
                "senderUsername" to message.senderUsername,
                "message" to message.message,
                "timestamp" to message.timestamp
            )
            db.collection("chats").document(chatId).collection("messages").add(messageMap)
            recyclerView.post {
                recyclerView.layoutManager?.scrollToPosition(messageAdapter.itemCount -1)
            }
        }*/ //è il metodo da implementare sul whether
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.chat_info) {
            val intent = Intent(this, ChatInfoActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}