package com.example.persistence

import android.content.Context
import androidx.room.Room
import com.example.persistence.dao.AdviceDao
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


}