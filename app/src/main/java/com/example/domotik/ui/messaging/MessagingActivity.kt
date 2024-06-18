package com.example.domotik.ui.messaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domotik.R
import com.google.android.material.textfield.TextInputEditText

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
                val message = Message(text)
                messageAdapter.addMessage(message)
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