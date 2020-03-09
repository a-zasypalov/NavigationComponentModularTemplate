package gaoyun.com.feature_advice_screen

import android.annotation.SuppressLint
import androidx.lifecycle.*
import gaoyun.com.data.Advice
import gaoyun.com.feature_advice_screen.data.AdviceUiModel
import gaoyun.com.feature_advice_screen.repository.AdviceRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@SuppressLint("CheckResult")
class AdviceViewModel: ViewModel() {

    @Inject
    lateinit var repository: AdviceRepository

    internal val adviceLiveData = MutableLiveData<AdviceUiModel>()
    private val disposables = CompositeDisposable()

    fun getRandomAdvice() {
        disposables.add(repository.getRandomAdvice()
                .doOnSubscribe {adviceLiveData.postValue(AdviceUiModel.Loading)}
                .subscribe(
                { advice ->
                    if (!advice.advice.isNullOrEmpty()) {
                        adviceLiveData.postValue(AdviceUiModel.Success(advice))
                    } else {
                        adviceLiveData.postValue(AdviceUiModel.Error(advice.error ?: "Error"))
                    }
                },
                { adviceLiveData.postValue(AdviceUiModel.Error(it.localizedMessage ?: "Error")) }
        ))
    }

    fun getAdviceById(id: Int) {
        disposables.add(repository.getAdviceById(id)
                .doOnSubscribe {adviceLiveData.postValue(AdviceUiModel.Loading)}
                .subscribe(
                { advice ->
                    if (!advice.advice.isNullOrEmpty()) {
                        adviceLiveData.postValue(AdviceUiModel.Success(advice))
                    } else {
                        adviceLiveData.postValue(AdviceUiModel.Error(advice.error ?: "Error"))
                    }
                },
                { adviceLiveData.postValue(AdviceUiModel.Error(it.localizedMessage ?: "Error")) }
        ))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}