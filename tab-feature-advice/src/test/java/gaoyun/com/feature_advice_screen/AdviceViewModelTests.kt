package gaoyun.com.feature_advice_screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.javafaker.Faker
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import gaoyun.com.data.Advice
import gaoyun.com.feature_advice_screen.data.AdviceUiModel
import gaoyun.com.feature_advice_screen.repository.AdviceRepository
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test

class AdviceViewModelTests {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository: AdviceRepository = mock()
    private val viewModel = AdviceViewModel(mockRepository)
    private val faker = Faker()

    @Test
    fun getRandomAdvice_CallsRepository(){
        val advice = Advice(1, faker.lorem().sentence(), null)
        whenever(mockRepository.getRandomAdvice()).thenReturn(Single.just(advice))

        viewModel.getRandomAdvice()

        verify(mockRepository).getRandomAdvice()
    }

    @Test
    fun getRandomAdvice_ReturnsRepository(){
        val advice = Advice(1, faker.lorem().sentence(), null)
        whenever(mockRepository.getRandomAdvice()).thenReturn(Single.just(advice))

        val mockObserver =  mock<Observer<AdviceUiModel>>()
        viewModel.adviceLiveData.observeForever(mockObserver)

        viewModel.getRandomAdvice()

        verify(mockObserver).onChanged(AdviceUiModel.Success(advice))
    }

}