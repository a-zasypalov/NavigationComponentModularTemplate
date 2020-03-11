package gaoyun.com.network.domain

import gaoyun.com.data.Advice
import gaoyun.com.network.repository.AdviceRemoteRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AdviceRemoteRepositoryInteractor(private val repository: AdviceRemoteRepository) {

    fun getRandomAdvice(): Single<Advice> {
        return repository.getRandomAdvice()
                .subscribeOn(Schedulers.io())
                .map { Advice(it.slip?.slipId?.toInt(), it.slip?.advice, it.error?.text) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAdviceById(id: Int): Single<Advice> {
        return repository.getAdviceById(id.toString())
                .subscribeOn(Schedulers.io())
                .map { Advice(it.slip?.slipId?.toInt() ?: id, it.slip?.advice, it.error?.text) }
                .observeOn(AndroidSchedulers.mainThread())
    }

}