package gaoyun.com.network.domain

import gaoyun.com.data.Advice
import gaoyun.com.network.repository.AdviceRemoteRepository
import io.reactivex.Single

class AdviceRemoteRepositoryInteractor(private val repository: AdviceRemoteRepository) {

    fun getRandomAdvice(): Single<Advice> {
        return repository.getRandomAdvice()
                .map { Advice(it.slip?.slipId?.toInt(), it.slip?.advice, it.error?.text) }
    }

    fun getAdviceById(id: Int): Single<Advice> {
        return repository.getAdviceById(id.toString())
                .map { Advice(it.slip?.slipId?.toInt(), it.slip?.advice, it.error?.text) }
    }

}