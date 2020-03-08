package gaoyunde.com.network.repository

import gaoyunde.com.network.AdviceService
import gaoyunde.com.network.responses.AdviceObject
import io.reactivex.Single

interface AdviceRemoteRepository {
    fun getRandomAdvice(): Single<AdviceObject>
}

class AdviceRemoteRepositoryImpl(private val adviceService: AdviceService) : AdviceRemoteRepository {

    override fun getRandomAdvice(): Single<AdviceObject> {
        return adviceService.getRandomAdvice()
    }

}