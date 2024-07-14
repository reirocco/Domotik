package com.example.domotik.ui.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AutenticazioneViewModel : ViewModel() {
    lateinit var cUser: FirebaseUser

    //salvataggio di un NUOVO utente nel database
     fun registrazione(username: String, email: String, password: String) {
        if (FirebaseAuth.getInstance().currentUser != null) {
            cUser = FirebaseAuth.getInstance().currentUser!!
            val db =
                FirebaseFirestore.getInstance()
            val usersRef = db.collection("users")

            val userMap = hashMapOf(
                "username" to username,
                "email" to email,
                "password" to password
            )

            usersRef.document(cUser.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (!document.exists()) {
                        usersRef.document(cUser.uid).set(userMap)
                            .addOnSuccessListener {
                                // Operazione riuscita
                            }
                            .addOnFailureListener { e ->
                                // Gestisci l'errore
                            }
                    }
                } else {
                    // Gestisci l'errore se la lettura del documento non è riuscita
                }
            }
        }
    }
}

//"https://console.firebase.google.com/project/domotikapp-bb2db/firestore/data"
