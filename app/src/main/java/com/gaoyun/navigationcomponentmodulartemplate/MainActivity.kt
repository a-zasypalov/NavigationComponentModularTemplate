package com.gaoyun.navigationcomponentmodulartemplate

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.gaoyun.android.navigationadvancedsample.R
import com.gaoyun.navigationcomponentmodulartemplate.di.DaggerMainComponent
import com.gaoyun.navigationcomponentmodulartemplate.di.GlobalNavigatorModule
import com.gaoyun.navigationcomponentmodulartemplate.navigation.GlobalNavigator
import com.gaoyun.navigationcomponentmodulartemplate.navigation.GlobalNavigatorRouter
import com.google.android.play.core.splitcompat.SplitCompat
import gaoyun.com.core_utils.ComponentDependenciesProvider
import gaoyun.com.core_utils.HasComponentDependencies
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), GlobalNavigator, HasComponentDependencies {

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    private val globalRouter: GlobalNavigatorRouter by lazy {
        GlobalNavigatorRouter(findNavController(R.id.global_nav))
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerMainComponent.builder()
                .globalNavigatorModule(GlobalNavigatorModule(this, this))
                .build()
                .inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }

    override fun openDestinationGlobally() {
        globalRouter.openDestinationGlobally()
    }

}
