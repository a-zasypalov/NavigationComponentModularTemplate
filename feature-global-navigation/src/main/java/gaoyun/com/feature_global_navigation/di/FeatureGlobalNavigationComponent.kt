package gaoyun.com.feature_global_navigation.di

import dagger.Component
import gaoyun.com.core_utils.ComponentDependencies
import gaoyun.com.feature_global_navigation.GlobalNavigationStartFragment
import gaoyun.com.feature_global_navigation.navigation.FeatureGlobalNavigation

interface FeatureGlobalNavigationComponentDependencies : ComponentDependencies {
    fun globalNavigator() : FeatureGlobalNavigation
}

@Component(dependencies = [FeatureGlobalNavigationComponentDependencies::class])
interface FeatureGlobalNavigationComponent {
    fun inject(fragment: GlobalNavigationStartFragment)
}