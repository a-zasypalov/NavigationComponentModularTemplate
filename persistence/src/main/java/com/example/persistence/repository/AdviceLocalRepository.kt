package com.example.persistence.repository

import androidx.lifecycle.LiveData
import com.example.persistence.dao.AdviceDao
import com.example.persistence.data.AdviceEntity

interface AdviceLocalRepository {
    fun getAdviceById(id: Int): LiveData<AdviceEntity>
    fun getAllAdvices(): LiveData<List<AdviceEntity>>
    fun insertAdvice(advice: AdviceEntity)
    fun removeAdvice(advice: AdviceEntity)
}

class AdviceLocalRepositoryImpl(private val adviceDao: AdviceDao) : AdviceLocalRepository {

    override fun getAdviceById(id: Int): LiveData<AdviceEntity> {
        return adviceDao.getAdviceById(id)
    }

    override fun getAllAdvices(): LiveData<List<AdviceEntity>> {
        return adviceDao.getAdvices()
    }

    override fun insertAdvice(advice: AdviceEntity) {
        adviceDao.insertAdvice(advice)
    }

    override fun removeAdvice(advice: AdviceEntity) {
        adviceDao.removeAdvice(advice)
    }
}