package com.example.domotik.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.domotik.R
import com.example.domotik.databinding.FragmentLogoutBinding
import com.example.domotik.ui.authentication.AutenticazioneActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class Impostazioni: Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var btn: Button
    lateinit var textView: TextView
    lateinit var user: FirebaseUser
    private lateinit var binding: FragmentLogoutBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentLogoutBinding>(
            inflater,
            R.layout.fragment_logout, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.d("TAG", "crash auth")
            auth = FirebaseAuth.getInstance()
            btn = binding.logout
            textView = binding.username
            user = auth.currentUser!!
            if (user == null) {
                //qualcosa che manda al login
            } else {
                textView.setText(user.email)
            }

            btn.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(requireContext(), AutenticazioneActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        } else {
            textView = binding.username
            textView.setText("Utente non loggato")
            btn = binding.logout
            btn.setText("LOGIN")
            btn.gravity = Gravity.CENTER
            btn.setOnClickListener {
                val intent = Intent(requireContext(), AutenticazioneActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
}
/*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.domotik.R
import com.example.domotik.databinding.FragmentLogoutBindingImpl
import com.example.domotik.ui.authentication.AutenticazioneActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class Impostazioni : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var btn: Button
    lateinit var textView: TextView
    lateinit var user: FirebaseUser
    private lateinit var binding: FragmentLogoutBindingImpl


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentLogoutBindingImpl>(
            inflater,
            R.layout.fragment_logout, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        binding.apply {
            if (currentUser != null) {
                // L'utente è loggato
                Log.d("TAG", "User is logged in")
                username.text = currentUser.email
                logout.text = "LOGOUT"
                logout.setOnClickListener {
                    auth.signOut()
                    val intent = Intent(requireContext(), AutenticazioneActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            } else {
                // L'utente non è loggato
                Log.d("TAG", "User is not logged in")
                username.text = "Utente non loggato"
                logout.text = "LOGIN"
                logout.gravity = Gravity.CENTER
                logout.setOnClickListener {
                    val intent = Intent(requireContext(), AutenticazioneActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }
    if (FirebaseAuth.getInstance().currentUser != null) {
        Log.d("TAG", "crash auth")
        auth = FirebaseAuth.getInstance()
        btn = binding.logout
        textView = binding.username
        user = auth.currentUser!!
        if (user == null) {
            //qualcosa che manda al login

        } else {
            textView.setText(user.email)
        }

        btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), AutenticazioneActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    } else {

        textView = binding.username
        textView.setText("Utente non loggato")
        btn = binding.logout
        btn.setText("LOGIN")
        btn.gravity = Gravity.CENTER
        btn.setOnClickListener {
            val intent = Intent(requireContext(), AutenticazioneActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
}
*/

/*
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.domotik.R
import com.example.domotik.databinding.FragmentUpdateprofiloBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class Impostazioni : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentUpdateprofiloBinding

   lateinit var button_aggiorna: Button
    private lateinit var textview_password: EditText
    private lateinit var email: EditText
    lateinit var user: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_updateprofilo, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        binding.apply {
            if (currentUser != null) {
                // L'utente è loggato
                Log.d("TAG", "User is logged in")
                username.text = currentUser.email
                logout.text = "LOGOUT"
                button_aggiorna.setOnClickListener {
                    // Ottieni i nuovi dati inseriti dall'utente
                    val newEmail = email.text.toString().trim()
                    val newPassword = textview_password.text.toString().trim()
                    // Verifica che i campi non siano vuoti
                    if (newEmail.isNotEmpty() && newPassword.isNotEmpty()) {
                        // Aggiorna il profilo utente
                        updateUserEmail(newEmail)
                        updateUserPassword(newPassword)
                    } else {
                        Toast.makeText(requireContext(), "Please enter new email and password", Toast.LENGTH_SHORT).show()
                    }
                }
                logout.setOnClickListener {
                    auth.signOut()
                    // Gestione del logout, non rilevante per l'aggiornamento dei dati
                }
            } else {
                // L'utente non è loggato
                Log.d("TAG", "User is not logged in")
                username.text = "Utente non loggato"
                logout.text = "LOGIN"
                logout.gravity = Gravity.CENTER
                logout.setOnClickListener {
                    // Gestione del login, non rilevante per l'aggiornamento dei dati
                }
            }
        }
    }

    // Metodo per aggiornare l'email e la password dell'utente
    /*private fun updateUserProfile(newEmail: String, newPassword: String) {
       val user = auth.currentUser
        user?.let {
            // Aggiorna l'email
            it.updateEmail(newEmail).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Se l'email è stata aggiornata con successo, aggiorna anche la password
                    it.updatePassword(newPassword).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.e("TAG", "Error updating password", task.exception)
                            Toast.makeText(requireContext(), "Error updating password", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e("TAG", "Error updating email", task.exception)
                    Toast.makeText(requireContext(), "Error updating email", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun updateUserEmail(newEmail: String) {
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        user?.let {
            it.updateEmail(newEmail).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Email updated successfully.")
                } else {
                    println("Failed to update email.")
                }
            }
        }
    }
}
fun updateUserPassword(newPassword: String) {
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    user?.let {
        it.updatePassword(newPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Password updated successfully.")
            } else {
                println("Failed to update password.")
            }
        }
    }


    val profileUpdates = userProfileChangeRequest {
        //photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
    }

    user!!.updateProfile(profileUpdates)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "User profile updated.")
            }
        }


    val emailAddress = "user@example.com"

    Firebase.auth.sendPasswordResetEmail(emailAddress)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Email sent.")
            }
        }
}*/