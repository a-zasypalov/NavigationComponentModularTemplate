package gaoyun.com.network

import gaoyun.com.network.responses.AdviceObject
import io.reactivex.Single
import retrofit2.http.GET

interface AdviceService {

    @GET("advice")
    fun getRandomAdvice(): Single<AdviceObject>

}
