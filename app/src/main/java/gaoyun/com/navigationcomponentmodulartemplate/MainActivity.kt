package gaoyun.com.navigationcomponentmodulartemplate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import gaoyun.com.navigationcomponentmodulartemplate.di.GlobalNavigatorModule
import gaoyun.com.navigationcomponentmodulartemplate.navigation.GlobalNavigator
import gaoyun.com.navigationcomponentmodulartemplate.navigation.GlobalNavigatorRouter
import com.google.android.play.core.splitcompat.SplitCompat
import gaoyun.com.core_utils.ComponentDependenciesProvider
import gaoyun.com.core_utils.HasComponentDependencies
import gaoyun.com.navigationcomponentmodulartemplate.di.DaggerMainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), GlobalNavigator, HasComponentDependencies {

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    private val globalRouter: GlobalNavigatorRouter by lazy {
        GlobalNavigatorRouter(findNavController(R.id.global_nav))
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMainComponent.builder()
                .globalNavigatorModule(GlobalNavigatorModule(this, this))
                .build()
                .inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun openDestinationGlobally() {
        globalRouter.openDestinationGlobally()
    }

    override fun openAdviceDynamicAnimationGlobally() {
//        globalRouter.openAdviceDynamicAnimationGlobally()
        startActivity(Intent().setClassName(this, "gaoyun.com.dynamicfeature.DynamicActivity"))
    }

}
