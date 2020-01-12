package com.example.android.navigationadvancedsample.formscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.android.navigationadvancedsample.MainActivity
import com.example.android.navigationadvancedsample.R

class GlobalNavigationStartFragment : Fragment(R.layout.fragment_global_start) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.signup_btn).setOnClickListener {
            (activity as MainActivity).openRegisteredGlobally()
        }
    }

}
