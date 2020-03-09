package com.gaoyun.navigationcomponentmodulartemplate.di

import com.gaoyun.navigationcomponentmodulartemplate.navigation.GlobalNavigator
import com.gaoyun.navigationcomponentmodulartemplate.MainActivity
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import gaoyun.com.core_utils.ComponentDependencies
import gaoyun.com.core_utils.ComponentDependenciesKey
import gaoyun.com.feature_global_navigation.di.FeatureGlobalNavigationComponentDependencies
import gaoyun.com.feature_global_navigation.navigation.FeatureGlobalNavigation
import gaoyun.com.feature_advice_screen.di.AdviceDependencies
import gaoyun.com.network.di.NetworkModule

@Component(
        modules = [
            NetworkModule::class,
            GlobalNavigatorModule::class,
            MainComponentDependenciesModule::class
        ]
)
interface MainComponent
    : FeatureGlobalNavigationComponentDependencies,
        AdviceDependencies {

    fun inject(mainActivity: MainActivity)

}

@Module
class GlobalNavigatorModule(private val globalNavigation: GlobalNavigator) {

    @Provides
    fun provideGlobalNavigation(): GlobalNavigator = globalNavigation

    @Provides
    fun provideFeatureGlobalNavigation(): FeatureGlobalNavigation = globalNavigation

}

@Module
abstract class MainComponentDependenciesModule private constructor() {

    @Binds
    @IntoMap
    @ComponentDependenciesKey(FeatureGlobalNavigationComponentDependencies::class)
    abstract fun provideFeatureGlobalNavigationComponentDependencies(component: MainComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(AdviceDependencies::class)
    abstract fun provideAdviceDependencies(component: MainComponent): ComponentDependencies

}