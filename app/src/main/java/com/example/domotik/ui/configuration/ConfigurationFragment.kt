package com.example.domotik.ui.configuration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domotik.R
import com.example.domotik.databinding.FragmentConfigurationBinding
import com.example.domotik.ui.home.HomeViewModel
import kotlin.math.log

class ConfigurationFragment : Fragment() {

    private var _binding: FragmentConfigurationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val button_meteo = binding.confMeteo
        button_meteo.setOnClickListener{
            Log.v(getString(R.string.log), "pulsante configurazione meteo premuto")
            val popUpMeteo = PopUpMeteoFragment()
            popUpMeteo.show((activity as AppCompatActivity).supportFragmentManager," popUpMeteo")
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

