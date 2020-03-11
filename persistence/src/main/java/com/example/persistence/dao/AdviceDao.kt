package com.example.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.persistence.data.AdviceEntity
import io.reactivex.Single

@Dao
interface AdviceDao {

    @Query("SELECT * FROM advices")
    fun getAdvices(): Single<List<AdviceEntity>>

    @Query("SELECT * FROM advices WHERE id = :id")
    fun getAdviceById(id: Int): Single<AdviceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAdvice(vararg advice: AdviceEntity)

    @Query("DELETE FROM advices")
    fun clearAdvices()

    @Delete
    fun removeAdvice(vararg advice: AdviceEntity)

}