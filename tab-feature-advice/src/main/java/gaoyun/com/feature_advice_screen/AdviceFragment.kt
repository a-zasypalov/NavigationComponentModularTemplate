package gaoyun.com.feature_advice_screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
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

class AdviceFragment : Fragment(R.layout.fragment_advice) {

    @Inject
    lateinit var viewModel: AdviceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerAdviceComponent.builder()
                .adviceDependencies(findComponentDependencies())
                .adviceModule(AdviceModule())
                .build()
                .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetup()
    }

    private fun initialSetup() {
        setLoadingState()
        initAdviceSubscription()
        initActionButtons()
    }

    private fun setLoadingState(){
        pbLoading.show()
        llActions.hide()
        tvAdviceText.hide()
    }

    private fun setContentState(){
        pbLoading.hide()
        llActions.show()
        tvAdviceText.show()
    }

    private fun setErrorState(){
        pbLoading.hide()
        llActions.show()
        tvAdviceText.hide()
    }

    private fun initAdviceSubscription() {
        viewModel.adviceLiveData.observe(viewLifecycleOwner) {
            renderAdvice(it)
        }

        if(viewModel.adviceLiveData.value != null) {
            renderAdvice(viewModel.adviceLiveData.value!!)
        } else {
            viewModel.getRandomAdvice()
        }
    }

    private fun renderAdvice(model: AdviceUiModel) {
        when(model) {
            is AdviceUiModel.Loading -> setLoadingState()
            is AdviceUiModel.Success -> showAdvice(model.advice)
            is AdviceUiModel.Error -> showError(model.error)
        }
    }

    private fun initActionButtons() {
        btnGetAdviceByIdAction.setOnClickListener {
            if(etAdviceId.text?.isNotEmpty() == true) {
                viewModel.getAdviceById(etAdviceId.text.toString().toInt())
            }
        }
        btnGetRandomAdviceAction.setOnClickListener {
            viewModel.getRandomAdvice()
        }
    }

    private fun showAdvice(advice: Advice) {
        tvAdviceText.text = advice.advice
        setContentState()
    }

    private fun showError(error: String) {
        setErrorState()
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

}
