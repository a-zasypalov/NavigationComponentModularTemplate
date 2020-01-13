package com.gaoyun.navigationcomponentmodulartemplate.formscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.gaoyun.navigationcomponentmodulartemplate.MainActivity
import com.gaoyun.android.navigationadvancedsample.R

class GlobalNavigationStartFragment : Fragment(R.layout.fragment_global_start) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.signup_btn).setOnClickListener {
            (activity as MainActivity).openRegisteredGlobally()
        }
    }

}
