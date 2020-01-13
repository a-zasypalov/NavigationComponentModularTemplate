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

@Component(
        modules = [GlobalNavigatorModule::class, MainComponentDependenciesModule::class]
)
interface MainComponent
    : FeatureGlobalNavigationComponentDependencies {

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

}