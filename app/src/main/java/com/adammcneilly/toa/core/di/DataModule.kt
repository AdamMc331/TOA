package com.adammcneilly.toa.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.adammcneilly.toa.core.data.local.TOADatabase
import com.adammcneilly.toa.core.data.local.TaskDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideTOADatabase(
        @ApplicationContext
        applicationContext: Context,
    ): TOADatabase {
        return Room.databaseBuilder(
            applicationContext,
            TOADatabase::class.java,
            "toa-database.db",
        ).build()
    }

    @Provides
    fun provideTaskDAO(
        database: TOADatabase,
    ): TaskDAO {
        return database.taskDao()
    }

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext
        applicationContext: Context,
    ): SharedPreferences {
        return applicationContext.getSharedPreferences(
            "toa_preferences",
            Context.MODE_PRIVATE,
        )
    }
}
