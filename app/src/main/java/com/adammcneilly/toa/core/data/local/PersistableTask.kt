package com.adammcneilly.toa.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class PersistableTask(
    @PrimaryKey
    val id: String,
    val description: String,
    val scheduledDate: String,
    @ColumnInfo(
        defaultValue = "oliveira_dan10",
    )
    val autoMigrationColumn: String,
)
