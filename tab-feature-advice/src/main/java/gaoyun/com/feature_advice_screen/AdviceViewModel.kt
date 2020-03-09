package gaoyun.com.feature_advice_screen

import android.annotation.SuppressLint
import androidx.lifecycle.*
import gaoyun.com.data.Advice
import gaoyun.com.feature_advice_screen.data.AdviceUiModel
import gaoyun.com.feature_advice_screen.repository.AdviceRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@SuppressLint("CheckResult")
class AdviceViewModel @Inject constructor(private val repository: AdviceRepository) : ViewModel() {

    internal val adviceLiveData = MutableLiveData<AdviceUiModel>()
    private val disposables = CompositeDisposable()

    fun getRandomAdvice() {
        disposables.add(repository.getRandomAdvice().subscribe(
                { advice -> adviceLiveData.postValue(AdviceUiModel.Success(advice)) },
                { adviceLiveData.postValue(AdviceUiModel.Error(it.localizedMessage ?: "Error")) }
        ))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}