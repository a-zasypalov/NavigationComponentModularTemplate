
package com.example.android.navigationadvancedsample.listscreen

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.android.navigationadvancedsample.R
import com.example.android.navigationadvancedsample.listscreen.MyAdapter.Companion.USERNAME_KEY

class UserProfile : Fragment(R.layout.fragment_user_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString(USERNAME_KEY) ?: "Ali Connors"
        view.findViewById<TextView>(R.id.profile_user_name).text = name
    }
}
