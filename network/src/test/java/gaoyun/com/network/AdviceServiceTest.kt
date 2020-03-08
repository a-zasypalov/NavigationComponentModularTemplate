package gaoyun.com.network

import gaoyunde.com.network.AdviceService
import com.github.javafaker.Faker
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import gaoyunde.com.network.repository.AdviceRemoteRepositoryImpl
import gaoyunde.com.network.responses.AdviceObject
import gaoyunde.com.network.responses.SlipObject
import io.reactivex.Single
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val id = "109"
private const val advice = "Advice"
private const val testJson = "{\"slip\": {\"advice\":\"$advice\",\"slip_id\":\"$id\"}}"

class AdviceServiceTest {

    @get:Rule
    val mockWebServer = MockWebServer()

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(mockWebServer.url("/advice/"))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val adviceService by lazy {
        retrofit.create(AdviceService::class.java)
    }

    private val adviceServiceMock: AdviceService = mock()
    private val faker = Faker()
    private val repository = AdviceRemoteRepositoryImpl(adviceServiceMock)

    @Test
    fun getAdviceEmitsAdvice() {
        mockWebServer.enqueue(
                MockResponse()
                        .setBody(testJson)
                        .setResponseCode(200))

        val testObserver = adviceService.getRandomAdvice().test()
        testObserver.assertValue(AdviceObject(SlipObject(id, advice)))
    }

    @Test
    fun getRandomAdviceEmitsAdvice() {
        val advice = AdviceObject(SlipObject(faker.idNumber().valid(), faker.lorem().sentence()))

        whenever(adviceServiceMock.getRandomAdvice()).thenReturn(Single.just(advice))

        val testObserver = repository.getRandomAdvice().test()
        testObserver.assertValue(advice)
    }

}