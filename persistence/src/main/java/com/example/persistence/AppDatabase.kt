package com.example.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.persistence.dao.AdviceDao
import com.example.persistence.data.AdviceEntity

@Database(entities = [AdviceEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val adviceDao: AdviceDao

}