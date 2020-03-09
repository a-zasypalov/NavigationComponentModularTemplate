package gaoyun.com.network

import gaoyun.com.network.responses.AdviceObject
import gaoyun.com.network.responses.SlipObject
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

    @Test
    fun getRandomAdvice_EmitsAdvice() {
        mockWebServer.enqueue(
                MockResponse()
                        .setBody(testJson)
                        .setResponseCode(200))

        val testObserver = adviceService.getRandomAdvice().test()
        testObserver.assertValue(AdviceObject(SlipObject(id, advice)))
    }

}