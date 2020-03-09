package gaoyun.com.feature_info_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import gaoyun.com.core_utils.findComponentDependencies
import gaoyun.com.feature_info_screen.di.AdviceModule
import gaoyun.com.feature_info_screen.di.DaggerAdviceComponent
import javax.inject.Inject

class AdviceFragment : Fragment(R.layout.fragment_advice) {

    @Inject
    lateinit var viewModel: AdviceViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DaggerAdviceComponent.builder()
                .adviceDependencies(findComponentDependencies())
                .adviceModule(AdviceModule())
                .build()
                .inject(this)

        super.onViewCreated(view, savedInstanceState)
    }

}
