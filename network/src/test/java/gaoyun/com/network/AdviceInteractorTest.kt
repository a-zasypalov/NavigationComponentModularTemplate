package gaoyun.com.network

import com.github.javafaker.Faker
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import gaoyun.com.data.Advice
import gaoyun.com.network.domain.AdviceRemoteRepositoryInteractor
import gaoyun.com.network.repository.AdviceRemoteRepository
import gaoyun.com.network.responses.AdviceObject
import gaoyun.com.network.responses.ErrorMessage
import gaoyun.com.network.responses.SlipObject
import io.reactivex.Single
import org.junit.Test

class AdviceInteractorTest {

    private val faker = Faker()
    private val repositoryMock: AdviceRemoteRepository = mock()
    private val interactor = AdviceRemoteRepositoryInteractor(repositoryMock)

    private val id = 1
    private val idError = 0
    private val adviceText = faker.lorem().sentence()
    private val adviceErrorText = faker.lorem().sentence()

    private val adviceRemote = AdviceObject(SlipObject(id.toString(), adviceText))
    private val adviceRemoteError = AdviceObject(slip = null, error = ErrorMessage(faker.lorem().word(), adviceErrorText))

    private val advice = Advice(id, adviceText, null)
    private val adviceError = Advice(null, null, adviceErrorText)

    @Test
    fun getRandomAdvice_emitsAdvice() {
        whenever(repositoryMock.getRandomAdvice()).thenReturn(Single.just(adviceRemote))

        val tesObserver = interactor.getRandomAdvice().test()
        tesObserver.assertValue(advice)
    }

    @Test
    fun getAdviceByIdWithValidId_emitsAdvice() {
        whenever(repositoryMock.getAdviceById(id.toString())).thenReturn(Single.just(adviceRemote))

        val tesObserver = interactor.getAdviceById(id).test()
        tesObserver.assertValue(advice)
    }

    @Test
    fun getAdviceByIdWithInvalidId_emitsAdviceWithError() {
        whenever(repositoryMock.getAdviceById(idError.toString())).thenReturn(Single.just(adviceRemoteError))

        val tesObserver = interactor.getAdviceById(idError).test()
        tesObserver.assertValue(adviceError)
    }

}