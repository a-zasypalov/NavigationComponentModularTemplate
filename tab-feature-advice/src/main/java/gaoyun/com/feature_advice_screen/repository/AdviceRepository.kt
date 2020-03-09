package gaoyun.com.feature_advice_screen.repository

import gaoyun.com.data.Advice
import gaoyun.com.network.domain.AdviceRemoteRepositoryInteractor
import io.reactivex.Single
import javax.inject.Inject

class AdviceRepository @Inject constructor(val interactor: AdviceRemoteRepositoryInteractor) {

    fun getRandomAdvice(): Single<Advice> {

    }

    fun getAdviceById(id: Int): Single<Advice> {

    }

}