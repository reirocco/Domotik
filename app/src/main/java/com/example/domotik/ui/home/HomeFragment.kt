package com.example.domotik.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.domotik.R
import com.example.domotik.databinding.FragmentHomeBinding
import com.example.domotik.network.model.Weather
import com.example.domotik.ui.viewModel.WeatherApiViewModel
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
        Log.v(getString(R.string.log), "Create Home Fragment View")

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // ######################################################################################## INIZIO CHIAMATA APIWEATHER
        //Prendo il view model dove memorizzerò i dati
        val weatherApiViewModel = ViewModelProvider(this).get(WeatherApiViewModel::class.java)
        // Recupero i dati dalle API

        try {
            //weatherApiViewModel.getCurretWeatherWithMetric("Metric")  // use this to retrieve outdoor data from openweather
            weatherApiViewModel.getIndoorCurrentWeather()  // use this to retrieve indoor data from internal air control station
        } catch (e: Exception) {
            Log.v(getString(R.string.log), e.message.toString())
        }

        // Creo l'observer sul dato da modificare
        weatherApiViewModel.myCurrentResponse.observe(viewLifecycleOwner, Observer {
            //Log.d(getString(R.string.log), it.toString())
            viewModel.getUpdatedText(it)
        })
        // ######################################################################################## FINE CHIAMATA APIWEATHER

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
    }

    fun updater(updatedText: Weather) {
        binding.textNomeDispositivo.text = updatedText.name
        binding.textUmiditaBox.text = "Humidity: " + updatedText.main.humidity.toString() + "%"
        binding.textTemperaturaValore.text = updatedText.main.temp.toString() + "°"
        binding.textCo2Box.text = "Co2: : " + updatedText.main.co2.toString() + "ppm"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v(getString(R.string.log), "Destroy Home Fragment View")
        _binding = null
    }
}