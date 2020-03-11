package com.example.persistence.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.persistence.dao.AdviceDao
import com.example.persistence.data.AdviceEntity
import gaoyun.com.data.Advice
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface AdviceLocalRepository {
    fun getAdviceById(id: Int): Single<Advice>
    fun getAllAdvices(): Single<List<Advice>>
    fun insertAdvice(advice: AdviceEntity)
    fun removeAdvice(advice: AdviceEntity)
}

@SuppressLint("CheckResult")
class AdviceLocalRepositoryImpl(private val adviceDao: AdviceDao) : AdviceLocalRepository {

    override fun getAdviceById(id: Int): Single<Advice> {
        return adviceDao.getAdviceById(id)
                .subscribeOn(Schedulers.io())
                .map { Advice(it.id, it.advice) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAllAdvices(): Single<List<Advice>> {
        return adviceDao.getAdvices()
                .subscribeOn(Schedulers.io())
                .map { it.map { item -> Advice(item.id, item.advice) } }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertAdvice(advice: AdviceEntity) {
        Completable.fromAction {
            adviceDao.insertAdvice(advice)
        }.subscribeOn(Schedulers.io())
                .subscribe(
                        { Log.d(ContentValues.TAG, "Insert advice ok") },
                        { Log.d(ContentValues.TAG, "Insert advice error") }
                )
    }

    override fun removeAdvice(advice: AdviceEntity) {
        Completable.fromAction {
            adviceDao.removeAdvice(advice)
        }.subscribeOn(Schedulers.io())
                .subscribe(
                        { Log.d(ContentValues.TAG, "Remove advice ok") },
                        { Log.d(ContentValues.TAG, "Remove advice error") }
                )
    }
}