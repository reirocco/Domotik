package com.example.domotik.ui.messaging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domotik.R
import java.lang.IllegalArgumentException

class MessageAdapter(private val messages: MutableList<Message>, private val uid: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val MY_MESSAGE = 0
        const val OTHER_MESSAGE = 1 //Enumeratore
    }
    class MyMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.my_message_text)
    }

    class OtherMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.other_message_text)
        val nameTextView: TextView = itemView.findViewById(R.id.other_message_sender_name)
    }
    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if(message.senderUid == uid){
            MY_MESSAGE
        } else{
            OTHER_MESSAGE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            MY_MESSAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.my_message, parent, false)
                MyMessageViewHolder(view)
            }
            OTHER_MESSAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.other_message, parent, false)
                OtherMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            MY_MESSAGE -> {
                val myMessageViewHolder = holder as MyMessageViewHolder
                myMessageViewHolder.messageTextView.text = messages[position].message
            }
            OTHER_MESSAGE -> {
                val otherMessageViewHolder = holder as OtherMessageViewHolder
                otherMessageViewHolder.messageTextView.text = messages[position].message
                otherMessageViewHolder.nameTextView.text = messages[position].senderUsername
            }
        }
    }

    override fun getItemCount() = messages.size

    fun addMessage(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }
}

