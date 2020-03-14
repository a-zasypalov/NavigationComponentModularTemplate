package gaoyun.com.navigationcomponentmodulartemplate.di

import android.content.Context
import com.example.persistence.RoomModule
import gaoyun.com.navigationcomponentmodulartemplate.navigation.GlobalNavigator
import gaoyun.com.navigationcomponentmodulartemplate.MainActivity
import dagger.*
import dagger.multibindings.IntoMap
import gaoyun.com.core_utils.ComponentDependencies
import gaoyun.com.core_utils.ComponentDependenciesKey
import gaoyun.com.feature_global_navigation.di.FeatureGlobalNavigationComponentDependencies
import gaoyun.com.feature_global_navigation.navigation.FeatureGlobalNavigation
import gaoyun.com.feature_advice_screen.di.AdviceDependencies
import gaoyun.com.feature_advice_screen.navigation.AdviceGlobalNavigation
import gaoyun.com.network.di.NetworkModule

@Component(
        modules = [
            NetworkModule::class,
            RoomModule::class,
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
class GlobalNavigatorModule(private val globalNavigation: GlobalNavigator, private val context: Context) {

    @Provides
    fun provideGlobalNavigation(): GlobalNavigator = globalNavigation

    @Provides
    fun provideFeatureGlobalNavigation(): FeatureGlobalNavigation = globalNavigation

    @Provides
    fun provideAdviceGlobalNavigation(): AdviceGlobalNavigation = globalNavigation

    @Provides
    fun provideContext(): Context = context

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