package gaoyun.com.feature_advice_screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import gaoyun.com.core_utils.findComponentDependencies
import gaoyun.com.core_utils.hide
import gaoyun.com.core_utils.show
import gaoyun.com.data.Advice
import gaoyun.com.feature_advice_screen.data.AdviceUiModel
import gaoyun.com.feature_advice_screen.di.AdviceModule
import gaoyun.com.feature_advice_screen.di.DaggerAdviceComponent
import kotlinx.android.synthetic.main.fragment_advice.*

class AdviceFragment : Fragment(R.layout.fragment_advice), SplitInstallStateUpdatedListener {

    private val viewModel: AdviceViewModel by viewModels()

    private val moduleAssets by lazy { getString(R.string.feature_advice_full) }

    private lateinit var manager: SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerAdviceComponent.builder()
                .adviceDependencies(findComponentDependencies())
                .adviceModule(AdviceModule())
                .build()
                .inject(viewModel)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetup()

        manager = SplitInstallManagerFactory.create(context)
    }

    private fun initialSetup() {
        setLoadingState()
        initAdviceSubscription()
        initActionButtons()
    }

    private fun setLoadingState() {
        pbLoading.show()
        llActions.hide()
        tvAdviceText.hide()
        lvBuilding.hide()
        tvDynamicLoading.hide()
        etAdviceId.clearFocus()
    }

    private fun setBuildingState() {
        lvBuilding.show()
        tvDynamicLoading.show()
        llActions.hide()
        tvAdviceText.hide()
        etAdviceId.clearFocus()
    }

    private fun setContentState() {
        pbLoading.hide()
        llActions.show()
        tvAdviceText.show()
        lvBuilding.hide()
        tvDynamicLoading.hide()
    }

    private fun setErrorState() {
        pbLoading.hide()
        llActions.show()
        lvBuilding.hide()
        tvAdviceText.hide()
        tvDynamicLoading.hide()
    }

    private fun initAdviceSubscription() {
        viewModel.adviceLiveData.observe(viewLifecycleOwner) {
            renderAdvice(it)
        }

        if (viewModel.adviceLiveData.value != null) {
            renderAdvice(viewModel.adviceLiveData.value!!)
        } else {
            viewModel.getRandomAdvice()
        }
    }

    private fun renderAdvice(model: AdviceUiModel) {
        when (model) {
            is AdviceUiModel.Loading -> setLoadingState()
            is AdviceUiModel.Success -> showAdvice(model.advice)
            is AdviceUiModel.Error -> showError(model.error)
        }
    }

    private fun initActionButtons() {
        btnGetAdviceByIdAction.setOnClickListener {
            if (etAdviceId.text?.isNotEmpty() == true) {
                viewModel.getAdviceById(etAdviceId.text.toString().toInt())
            }
        }

        btnGetRandomAdviceAction.setOnClickListener {
            viewModel.getRandomAdvice()
        }

        btnShowFullscreen.setOnClickListener {
//            setBuildingState()
            if (manager.installedModules.contains(moduleAssets)) {
                viewModel.dynamicNavigation()
//                startDynamicActivity()
            } else {
                MaterialAlertDialogBuilder(context)
                        .setTitle("Load dynamic feature?")
                        .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                        .setPositiveButton("Yes") { dialog, _ ->
                            dialog.dismiss()
                            setBuildingState()
                            val request = SplitInstallRequest.newBuilder()
                                    .addModule(moduleAssets)
                                    .build()

                            manager.startInstall(request)
                                    .addOnSuccessListener {
                                        startDynamicActivity()
                                    }
                                    .addOnFailureListener { Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show() }

                        }
                        .create()
                        .show()
            }
        }
    }

    override fun onStateUpdate(state: SplitInstallSessionState?) {
        if(state?.errorCode() == SplitInstallErrorCode.NO_ERROR && state.status() == SplitInstallSessionStatus.INSTALLED) {
            startDynamicActivity()
        }
    }

    private fun startDynamicActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.dynamicNavigation()
        }, 3000)
        Handler(Looper.getMainLooper()).postDelayed({
            setContentState()
        }, 4000)
    }

    private fun showAdvice(advice: Advice) {
        tvAdviceText.text = advice.advice
        setContentState()
    }

    private fun showError(error: String) {
        setErrorState()
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

}
