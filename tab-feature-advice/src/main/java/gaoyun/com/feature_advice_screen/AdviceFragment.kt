package gaoyun.com.feature_advice_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.jakewharton.rxbinding2.view.clicks
import gaoyun.com.core_utils.findComponentDependencies
import gaoyun.com.core_utils.hide
import gaoyun.com.core_utils.show
import gaoyun.com.data.Advice
import gaoyun.com.feature_advice_screen.data.AdviceUiModel
import gaoyun.com.feature_advice_screen.di.AdviceModule
import gaoyun.com.feature_advice_screen.di.DaggerAdviceComponent
import kotlinx.android.synthetic.main.fragment_advice.*
import javax.inject.Inject

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
        etAdviceId.clearFocus()
    }

    private fun setBuildingState() {
        lvBuilding.show()
        llActions.hide()
        tvAdviceText.hide()
        etAdviceId.clearFocus()
    }

    private fun setContentState() {
        pbLoading.hide()
        llActions.show()
        tvAdviceText.show()
    }

    private fun setErrorState() {
        pbLoading.hide()
        llActions.show()
        tvAdviceText.hide()
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
            if (manager.installedModules.contains(moduleAssets)) {
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                startDynamicActivity()
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
                                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
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
            viewModel.dynamicNavigation()
        }
    }

    private fun startDynamicActivity() {
        viewModel.dynamicNavigation()
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
