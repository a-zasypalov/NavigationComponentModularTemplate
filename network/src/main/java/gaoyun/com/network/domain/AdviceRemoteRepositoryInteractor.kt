package gaoyun.com.network.domain

import gaoyun.com.data.Advice
import gaoyun.com.network.repository.AdviceRemoteRepository
import io.reactivex.Single

class AdviceRemoteRepositoryInteractor(private val repository: AdviceRemoteRepository) {

    fun getRandomAdvice(): Single<Advice> {
    }

}