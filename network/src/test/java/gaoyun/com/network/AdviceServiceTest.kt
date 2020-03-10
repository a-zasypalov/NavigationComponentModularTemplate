package gaoyun.com.network

import gaoyun.com.network.data.AdviceObject
import gaoyun.com.network.data.ErrorMessage
import gaoyun.com.network.data.SlipObject
import gaoyun.com.network.service.AdviceService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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

    private val id = "109"
    private val invalidId = "0"

    private val advice = "Advice"
    private val testJson = "{\"slip\": {\"advice\":\"$advice\",\"slip_id\":\"$id\"}}"

    private val errorType = "error"
    private val errorText = "Advice slip not found."
    private val testErrorJson = "{\"message\": {\"type\": \"$errorType\", \"text\": \"$errorText\"}}"

    @Test
    fun getRandomAdvice_emitsAdvice() {
        mockWebServer.enqueue(
                MockResponse()
                        .setBody(testJson)
                        .setResponseCode(200))

        val testObserver = adviceService.getRandomAdvice().test()
        testObserver.assertValue(AdviceObject(SlipObject(id, advice)))
    }

    @Test
    fun getAdviceByIdWithValidId_emitsAdvice() {
        mockWebServer.enqueue(
                MockResponse()
                        .setBody(testJson)
                        .setResponseCode(200))

        val testObserver = adviceService.getAdviceById(id).test()
        testObserver.assertValue(AdviceObject(SlipObject(id, advice)))
    }

    @Test
    fun getAdviceByIdWithInvalidId_emitsAdvice() {
        mockWebServer.enqueue(
                MockResponse()
                        .setBody(testErrorJson)
                        .setResponseCode(200))

        val testObserver = adviceService.getAdviceById(invalidId).test()
        testObserver.assertValue(AdviceObject(error = ErrorMessage(errorType, errorText)))
    }

}