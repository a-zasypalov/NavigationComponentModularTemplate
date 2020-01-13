
package com.gaoyun.navigationcomponentmodulartemplate

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.gaoyun.android.navigationadvancedsample.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    fun openRegisteredGlobally(){
        findNavController(R.id.global_nav).navigate(R.id.action_tabsFragment_to_global_destination)
    }

}
