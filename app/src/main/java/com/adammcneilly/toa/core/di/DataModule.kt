package com.adammcneilly.toa.core.di

import android.content.Context
import androidx.room.Room
import com.adammcneilly.toa.Database
import com.adammcneilly.toa.core.data.local.TOADatabase
import com.adammcneilly.toa.core.data.local.TaskDAO
import com.adammcneilly.toa.sqldelight.TaskQueries
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
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
    @Singleton
    fun provideSQLDelightDatabase(
        @ApplicationContext
        applicationContext: Context,
    ): Database {
        val driver: SqlDriver = AndroidSqliteDriver(
            schema = Database.Schema,
            context = applicationContext,
            name = "toa-database-sqldelight.db",
        )

        return Database(driver)
    }

    @Provides
    fun provideTaskQueries(
        database: Database,
    ): TaskQueries {
        return database.taskQueries
    }
}
