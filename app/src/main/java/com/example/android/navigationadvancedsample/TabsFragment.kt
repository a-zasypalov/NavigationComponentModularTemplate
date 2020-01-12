package com.example.android.navigationadvancedsample

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class TabsFragment : Fragment(R.layout.fragment_tabs) {

    private var currentNavController: LiveData<NavController>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentNavController?.value?.let {
                    if (it.currentDestination?.id == R.id.blankFragment
                            || it.currentDestination?.id == R.id.localNavigationStart
                            || it.currentDestination?.id == R.id.register) {
                        this.isEnabled = false
                        requireActivity().finish()
                    } else {
                        it.navigateUp()
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = requireView().findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(gaoyun.com.feature_home.R.navigation.local, R.navigation.blank, R.navigation.global)

        val controller = bottomNavigationView.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = childFragmentManager,
                containerId = R.id.nav_host_container,
                intent = requireActivity().intent
        )

        currentNavController = controller
    }

}