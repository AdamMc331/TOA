package com.adammcneilly.toa.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    @Query("SELECT * FROM task")
    suspend fun fetchAllTasks(): Flow<List<PersistableTask>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
    )
    suspend fun insertTask(task: PersistableTask)
}
