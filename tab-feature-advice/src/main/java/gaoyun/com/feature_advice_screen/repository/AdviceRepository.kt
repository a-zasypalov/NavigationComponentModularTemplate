package gaoyun.com.feature_advice_screen.repository

import com.example.persistence.data.AdviceEntity
import com.example.persistence.repository.AdviceLocalRepository
import gaoyun.com.data.Advice
import gaoyun.com.network.domain.AdviceRemoteRepositoryInteractor
import io.reactivex.Single
import javax.inject.Inject

class AdviceRepository @Inject constructor(
        private val interactor: AdviceRemoteRepositoryInteractor,
        private val localRepository: AdviceLocalRepository
) {

    fun getRandomAdvice(): Single<Advice> {
        return interactor.getRandomAdvice()
    }

    fun getAdviceById(id: Int): Single<Advice> {
        return localRepository.getAdviceById(id)
                .onErrorResumeNext(interactor.getAdviceById(id))
    }

    fun saveAdvice(advice: Advice?) {
        advice?.let {
            localRepository.insertAdvice(AdviceEntity(it.id!!, it.advice!!))
        }
    }

}