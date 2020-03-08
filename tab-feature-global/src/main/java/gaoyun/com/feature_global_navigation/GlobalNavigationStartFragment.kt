package gaoyun.com.feature_global_navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import gaoyun.com.core_utils.findComponentDependencies
import gaoyun.com.feature_global_navigation.di.DaggerFeatureGlobalNavigationComponent
import gaoyun.com.feature_global_navigation.navigation.FeatureGlobalNavigation
import javax.inject.Inject

class GlobalNavigationStartFragment : Fragment(R.layout.fragment_global_start) {

    @Inject
    lateinit var globalNavigator: FeatureGlobalNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerFeatureGlobalNavigationComponent.builder()
                .featureGlobalNavigationComponentDependencies(findComponentDependencies())
                .build()
                .inject(this)

        view.findViewById<View>(R.id.signup_btn).setOnClickListener {
            globalNavigator.openDestinationGlobally()
        }
    }

}
