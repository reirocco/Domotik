package com.example.domotik.ui.home
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domotik.MainActivity
import com.example.domotik.R
import com.example.domotik.databinding.FragmentHomeBinding
import com.example.domotik.ui.camera.CameraActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.v("@string/log","Create Home Fragment View")
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textNomeCitta: TextView = binding.textNomeCitta
        homeViewModel.text.observe(viewLifecycleOwner) {
            textNomeCitta.text = it
        } // this is for the example


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v("@string/log","Destroy Home Fragment View")
        _binding = null
    }
}