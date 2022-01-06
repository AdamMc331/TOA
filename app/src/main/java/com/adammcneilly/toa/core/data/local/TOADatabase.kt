package com.adammcneilly.toa.core.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PersistableTask::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ],
)
abstract class TOADatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDAO
}
