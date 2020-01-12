
package com.example.android.navigationadvancedsample.formscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.navigationadvancedsample.MainActivity
import com.example.android.navigationadvancedsample.R

class Register : Fragment(R.layout.fragment_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.signup_btn).setOnClickListener {
            (activity as MainActivity).openRegisteredGlobally()
        }
    }

}
