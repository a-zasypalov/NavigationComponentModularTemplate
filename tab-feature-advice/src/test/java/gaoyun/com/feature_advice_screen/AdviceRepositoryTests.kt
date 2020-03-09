package gaoyun.com.feature_advice_screen

import com.github.javafaker.Faker
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import gaoyun.com.data.Advice
import gaoyun.com.feature_advice_screen.repository.AdviceRepository
import gaoyun.com.network.domain.AdviceRemoteRepositoryInteractor
import io.reactivex.Single
import org.junit.Test

class AdviceRepositoryTests {

    private val adviceInteractorMock: AdviceRemoteRepositoryInteractor = mock()
    private val faker = Faker()
    private val repository = AdviceRepository(adviceInteractorMock)

    @Test
    fun getRandomAdviceEmitsAdvice() {
        val advice = Advice(1, faker.lorem().sentence(), null)

        whenever(adviceInteractorMock.getRandomAdvice()).thenReturn(Single.just(advice))

        val testObserver = repository.getRandomAdvice().test()
        testObserver.assertValue(advice)
    }

    @Test
    fun getAdviceByIdWithValidId_emitsAdvice() {
        val advice = Advice(1, faker.lorem().sentence(), null)

        whenever(adviceInteractorMock.getAdviceById(advice.id!!)).thenReturn(Single.just(advice))

        val testObserver = repository.getAdviceById(advice.id!!).test()
        testObserver.assertValue(advice)
    }

    @Test
    fun getAdviceByIdWithNotValidId_emitsError() {
        val error = Advice(error = "error", advice = null, id = null)
        val invalidId = 0

        whenever(adviceInteractorMock.getAdviceById(invalidId)).thenReturn(Single.just(error))

        val testObserver = repository.getAdviceById(invalidId).test()
        testObserver.assertValue(error)
    }

}