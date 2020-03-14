package gaoyun.com.navigationcomponentmodulartemplate.navigation

import androidx.navigation.NavController
import gaoyun.com.feature_advice_screen.navigation.AdviceGlobalNavigation
import gaoyun.com.feature_global_navigation.navigation.FeatureGlobalNavigation
import gaoyun.com.navigationcomponentmodulartemplate.R

interface GlobalNavigator : FeatureGlobalNavigation, AdviceGlobalNavigation

class GlobalNavigatorRouter(private val navController: NavController) : GlobalNavigator {

    override fun openDestinationGlobally() {
        navController.navigate(R.id.action_tabsFragment_to_global_destination)
    }

    override fun openAdviceDynamicAnimationGlobally() {

    }

}