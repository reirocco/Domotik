/*
* SEE MORE ON https://www.google.com/search?q=popup+kotlin&oq=popup+kotlin&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTIICAEQABgWGB4yCAgCEAAYFhgeMggIAxAAGBYYHjIICAQQABgWGB4yCAgFEAAYFhgeMggIBhAAGBYYHjIICAcQABgWGB4yCAgIEAAYFhgeMggICRAAGBYYHtIBCDUzNDBqMGo0qAIAsAIA&sourceid=chrome&ie=UTF-8#fpstate=ive&vld=cid:730734fd,vid:db7-AyqvaLA,st:0
* OR REFERENCE
* */

package com.example.domotik.ui.configuration

import android.os.Bundle
import android.view.LayoutInflater
                import android.view.View
                import android.view.ViewGroup
                import androidx.fragment.app.DialogFragment
                import com.example.domotik.R

            class PopUpMeteoFragment : DialogFragment() {
            override fun onCreateView(
                inflater: LayoutInflater,
                container:ViewGroup?,
                savedInstanceState: Bundle?
            ): View? {
                return inflater.inflate(R.layout.fragment_pop_up, container, false)
    }
}
