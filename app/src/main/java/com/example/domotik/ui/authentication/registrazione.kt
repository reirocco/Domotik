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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.domotik.MainActivity
import com.example.domotik.R
import com.example.domotik.databinding.FragmentRegistrazioneBinding
import com.example.domotik.ui.viewModel.AutenticazioneViewModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class registrazione : Fragment() {
    lateinit var User : EditText
    lateinit var Email : EditText
    lateinit var Password : EditText
    lateinit var buttonReg : Button
    lateinit var mAuth : FirebaseAuth
    private lateinit var viewModel: AutenticazioneViewModel
    private lateinit var binding: FragmentRegistrazioneBinding

    public override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    interface ClickListener {
        fun onScrittaClicked()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_registrazione,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AutenticazioneViewModel::class.java)

        binding.clickListener = object : ClickListener {
            override fun onScrittaClicked() {
                if (isAdded) {
                    Navigation.findNavController(view).navigate(R.id.action_registrazione_to_login)
                }
            }
        }
        mAuth = FirebaseAuth.getInstance()

        User = binding.username
        Email = binding.email
        Password = binding.password
        buttonReg = binding.btnRegister

        buttonReg.setOnClickListener {
            val username = User.text.toString()
            val email = Email.text.toString()
            val password = Password.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Compila tutti i campi", Toast.LENGTH_SHORT).show()
                }
                return@setOnClickListener
            }

            if (email.isNotEmpty() && password.isNotEmpty()) {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            if (isAdded) {
                                Toast.makeText(
                                    requireContext(),
                                    "Account creato!",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }

                            val user = task.result?.user
                            val db = Firebase.firestore
                            if (user != null) {
                                val userMap = hashMapOf(
                                    "username" to username,
                                    "email" to email
                                )
                                db.collection("users").document(user.uid).set(userMap).addOnSuccessListener {
                                    logUserAction("registration", email)
                                    if (isAdded) {
                                        Navigation.findNavController(view)
                                            .navigate(R.id.action_registrazione_to_login)
                                    }
                                }
                            }

                        } else {
                            val exception = task.exception
                            if (exception != null && isAdded) {
                                val errorMessage = exception.localizedMessage
                                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            } else {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Inserisci email e password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun logUserAction(action: String, email: String) {
        val db = Firebase.firestore
        val log = hashMapOf(
            "email" to email,
            "action" to action,
            "timestamp" to Timestamp.now()
        )
        db.collection("user_logs").add(log)
            .addOnSuccessListener {
                if (isAdded) {
                    Toast.makeText(
                        requireContext(),
                        "Log aggiunto correttamente!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Log fallito!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
