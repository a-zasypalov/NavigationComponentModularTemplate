package com.example.network

import com.example.data.AdviceObject
import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("advice")
    fun getRandomAdvice(): Single<AdviceObject>

}
