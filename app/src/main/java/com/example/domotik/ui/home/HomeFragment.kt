package com.example.domotik.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.domotik.ui.viewModel.WeatherViewModel
import com.example.domotik.databinding.FragmentHomeBinding
import com.example.domotik.network.model.Coord
import com.example.domotik.network.model.Weather

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Create a viewModel
    private val viewModel: HomeViewModel = HomeViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.v("@string/logging", "Create Home Fragment View")
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        /*val textNomeCitta: TextView = binding.textNomeCitta
        homeViewModel.text.observe(viewLifecycleOwner) {
            textNomeCitta.text = it
        } // this is for the example
         */


        // FINE CHIAMATA API USER DI TEST

        // INIZIO CHIAMATA APIWEATHER
        //Prendo il view model dove memorizzerò i dati
        val weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        // Recupero i dati dalle API
        weatherViewModel.getCurretWeather()
        // Creo l'observer sul dato da modificare
        weatherViewModel.myResponse.observe(viewLifecycleOwner, Observer {
            Log.d("@String/log", "dt: " + it.dt)
            Log.d("@String/log", "lat: " + it.coord.lat)
            Log.d("@String/log", "lon: " + it.coord.lon)
            Log.d("@string/log", "city: " + it.name)
            Log.d("@string/log", "humidità: " + it.main.humidity)
            viewModel.getUpdatedText(it)
        })


        // FINE CHIAMATA APIWEATHER


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTextUpdateObserver()
    }


    // Observer is waiting for viewModel to update our UI
    private fun fragmentTextUpdateObserver() {
        viewModel.uiLiveData.observe(
            viewLifecycleOwner,
            Observer { updatedText -> updater(updatedText) })

        /*viewModel.uiTextLiveData.observe(viewLifecycleOwner, Observer { updatedText ->
            binding.textNomeCitta.text = updatedText
        })
        */
    }

    fun updater(updatedText: Weather) {
        binding.textNomeCitta.text = updatedText.name
        binding.textUmiditaBox.text = "Hum: " + updatedText.main.humidity.toString() + "%"
        binding.textTemperaturaValore.text = updatedText.main.temp.toString() + "°"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v("@string/log", "Destroy Home Fragment View")
        _binding = null
    }
}