package com.edloca.fastask.core

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.edloca.fastask.database.TaskDao
import com.edloca.fastask.database.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideChannelDao(appDatabase: TaskDatabase): TaskDao {
        return appDatabase.taskDao()
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context):TaskDatabase{
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
                "TaskDB"
        ).build()
    }
}