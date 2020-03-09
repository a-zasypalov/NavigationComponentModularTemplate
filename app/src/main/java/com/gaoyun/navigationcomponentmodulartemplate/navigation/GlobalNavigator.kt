package com.gaoyun.navigationcomponentmodulartemplate.navigation

import androidx.navigation.NavController
import com.gaoyun.android.navigationadvancedsample.R
import gaoyun.com.feature_global_navigation.navigation.FeatureGlobalNavigation

interface GlobalNavigator : FeatureGlobalNavigation

class GlobalNavigatorRouter(private val navController: NavController) : GlobalNavigator {

    override fun openDestinationGlobally() {
        navController.navigate(R.id.action_tabsFragment_to_global_destination)
    }

}