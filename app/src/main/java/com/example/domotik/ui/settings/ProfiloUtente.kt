package com.example.domotik.ui.settings

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.domotik.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore



class ProfiloUtente : AppCompatActivity() {

    private lateinit var Nome: EditText
    private lateinit var Cognome: EditText
    private lateinit var email: EditText
    private lateinit var indirizzo: EditText
    private lateinit var password: EditText
    private lateinit var buttonAggiorna: Button

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_updateprofilo)
        FirebaseApp.initializeApp(this)


        // Initialize views
        Nome = findViewById(R.id.FirstNameEditText)
        //User =findViewById(R.id.username)
        Cognome = findViewById(R.id.cognome)
        email = findViewById(R.id.email)
        indirizzo = findViewById(R.id.indirizzo)
        password = findViewById(R.id.password)
        buttonAggiorna = findViewById(R.id.button_Aggiorna)

        /*buttonAggiorna.setOnClickListener {
            updateProfile()
        }*/

        /*val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            Nome.setText(it.nome)
            email.setText(it.email)
            password.setText(it.password)
            indirizzo.setText(it.indirizzo)
            // Set other fields if needed
        }*/
        buttonAggiorna.setOnClickListener {
            val nuovoNome = Nome.text.toString()
            val nuovaEmail = email.text.toString()
            val nuovoIndirizzo = indirizzo.text.toString()
            val nuovaPassword = password.text.toString()
            val nuovoCognome = Cognome.text.toString()


        }
            // Creare un HashMap con i nuovi dati da aggiornare nel documento Firestore
            val newData = hashMapOf(
                //"username" to Nome,
                "email" to email,
                "nome" to Nome,
                "indirizzo" to indirizzo,
                "password" to password,
                "cognome" to Cognome
                // Aggiungi altri campi se necessario
            )
        val user = FirebaseAuth.getInstance().currentUser
        //val db = FirebaseFirestore.getInstance()
        val userDocRef = db.collection("Utenti").document( "dati_utente")

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val username = document.getString("username")
                    val nome = document.getString("nome")
                    val cognome = document.getString("cognome")
                    val indirizzo = document.getString("indirizzo")
                    val password = document.getString("password")
                    val email = document.getString("email")
                    // Esegui le azioni desiderate con i dati ottenuti da Firestore
                } else {
                    println(" Non è possibile effettuare questa operazione")// Il documento non esiste
                }
            }
            .addOnFailureListener { exception ->

                // Gestisci eventuali errori
            }
    }
}
       /* userDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val nome = documentSnapshot.getString("Nome")
                //val username= documentSnapshot.getString("Username")
                val cognome = documentSnapshot.getString("Cognome")
                val userEmail = documentSnapshot.getString("email")
                val userIndirizzo = documentSnapshot.getString("indirizzo")
                val userPassword = documentSnapshot.getString("password")

                // Set text to EditText fields
                Nome.setText(nome)
                //Username.setText(username)
                Cognome.setText(cognome)
                email.setText(userEmail)
                indirizzo.setText(userIndirizzo)
                password.setText(userPassword)
            } else {
                // Document doesn't exist
            }
        }.addOnFailureListener { exception ->
            // Handle failures
        }
    }

    // Set click listener for the update button


    private fun updateProfile() {
        val user = FirebaseAuth.getInstance().currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(Nome.text.toString() + " " + Cognome.text.toString() + email.text.toString() + "" + indirizzo.text.toString() + "" + password.text.toString() + " " )
            .build()

        /* buttonAggiorna.setOnClickListener {*/

        val name = Nome.text.toString()
        //val uername = Username.text.toString()
        val cognome = Cognome.text.toString()
        val email = email.text.toString()
        val address = indirizzo.text.toString()
        val password = password.text.toString()

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Update successful
                    println("Profilo aggiornato")
                    // You can also update email and address similarly if needed
                } else {
                    println("Si è verificato qualche errore durante la modifica del profilo")
                    // Update failed
                }

            }

    }*/
