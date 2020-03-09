package com.example.persistence

import android.content.Context
import androidx.room.Room
import com.example.persistence.dao.AdviceDao
import com.example.persistence.repository.AdviceLocalRepository
import com.example.persistence.repository.AdviceLocalRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    internal fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()
    }

    @Provides
    internal fun provideAdviceDao(appDatabase: AppDatabase): AdviceDao {
        return appDatabase.adviceDao
    }

    @Provides
    internal fun provideAdviceLocalRepository(dao: AdviceDao): AdviceLocalRepository {
        return AdviceLocalRepositoryImpl(dao)
    }

}