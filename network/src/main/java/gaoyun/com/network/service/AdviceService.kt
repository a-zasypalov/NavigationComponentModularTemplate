package gaoyun.com.network.service

import gaoyun.com.network.data.AdviceObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface AdviceService {

    @GET("advice")
    fun getRandomAdvice(): Single<AdviceObject>

    @GET("advice/{id}")
    fun getAdviceById(@Path("id") id: String): Single<AdviceObject>

}
