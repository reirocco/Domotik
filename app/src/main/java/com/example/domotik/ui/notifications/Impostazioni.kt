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
import com.example.domotik.ui.authentication.AutenticazioneActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.domotik.databinding.FragmentImpostazioniBinding


class Impostazioni : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var btn: Button
    lateinit var textView: TextView
    lateinit var user: FirebaseUser
    private lateinit var binding: FragmentImpostazioniBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentImpostazioniBinding>(
            inflater,
            R.layout.fragment_impostazioni, container, false
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