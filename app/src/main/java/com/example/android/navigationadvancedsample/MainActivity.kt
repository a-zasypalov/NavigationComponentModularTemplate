
package com.example.android.navigationadvancedsample

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    fun openRegisteredGlobally(){
        findNavController(R.id.global_nav).navigate(R.id.action_tabsFragment_to_global_destination)
    }

}
