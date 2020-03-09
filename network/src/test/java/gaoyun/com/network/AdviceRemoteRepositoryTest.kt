package gaoyun.com.network

import com.github.javafaker.Faker
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import gaoyun.com.network.repository.AdviceRemoteRepositoryImpl
import gaoyun.com.network.responses.AdviceObject
import gaoyun.com.network.responses.SlipObject
import io.reactivex.Single
import org.junit.Test

class AdviceRemoteRepositoryTest {

    private val adviceServiceMock: AdviceService = mock()
    private val faker = Faker()
    private val repository = AdviceRemoteRepositoryImpl(adviceServiceMock)

    @Test
    fun getRandomAdviceFromRepository_EmitsAdvice() {
        val advice = AdviceObject(SlipObject(faker.idNumber().valid(), faker.lorem().sentence()))

        whenever(adviceServiceMock.getRandomAdvice()).thenReturn(Single.just(advice))

        val testObserver = repository.getRandomAdvice().test()
        testObserver.assertValue(advice)
    }

}