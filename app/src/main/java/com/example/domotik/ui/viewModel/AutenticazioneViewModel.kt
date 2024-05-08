package com.example.domotik.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.domotik.ui.dataModel.Utenti
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AutenticazioneViewModel : ViewModel() {
    lateinit var cUser: FirebaseUser

    //salvataggio di un NUOVO utente nel database
    fun registrazione(username: String, password: String, email: String) {
        if (FirebaseAuth.getInstance().currentUser != null) {
            cUser = FirebaseAuth.getInstance().currentUser!!
            val database =
                FirebaseDatabase.getInstance("https://console.firebase.google.com/project/domotikapp-bb2db/firestore/data")
            val usersRef = database.reference.child("Utenti")

            usersRef.child(cUser.uid ?: "")
                .addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (!snapshot.exists()) {

                            val user = Utenti(
                                email = email,
                                password = password,
                                username = username
                            )
                            usersRef.child(cUser.uid).setValue(user)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }

    }
}
