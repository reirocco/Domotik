package com.example.domotik.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.domotik.MainActivity
import com.example.domotik.R
import com.example.domotik.databinding.FragmentLoginBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    lateinit var Nome: EditText
    lateinit var Cognome: EditText
    lateinit var Email : EditText
    lateinit var Password : EditText
    lateinit var buttonLogin : Button
    lateinit var mAuth : FirebaseAuth


    public override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    interface ClickListenerLogin {
        fun onScrittaClicked()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,
            R.layout.fragment_login,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clickListenerLogin = object : ClickListenerLogin {
            override fun onScrittaClicked() {
                Navigation.findNavController(view).navigate(R.id.action_login_to_registrazione)
            }
        }
        val db = Firebase.firestore
        mAuth = FirebaseAuth.getInstance()

        Email = binding.email
        Password = binding.password
        buttonLogin = binding.btnLogin

        buttonLogin.setOnClickListener {
            val email = Email.text.toString()
            val password = Password.text.toString()


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Compila tutti i campi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isNotEmpty() && password.isNotEmpty()) {

                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Login effettuato!",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val user =task.result.user
                            if (user != null){
                                logUserAction(user.uid, "login", email)
                            }
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Login fallito!",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Inserisci email e password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun logUserAction(action: String, details: String, email: String) {
        val db = Firebase.firestore
        val log = hashMapOf(
            "email" to email,
            "action" to action,
            "details" to details,
            "timestamp" to Timestamp.now()
        )
        db.collection("log_users").add(log)
            .addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Log aggiunto correttamente!",
                    Toast.LENGTH_SHORT,
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Log fallito!",
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }
}



