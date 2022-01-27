package com.adammcneilly.toa.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    @Query("SELECT * FROM task")
    fun fetchAllTasks(): Flow<List<PersistableTask>>

    @Query("SELECT * FROM task WHERE scheduledDate = :date")
    fun fetchTasksForDate(date: String): Flow<List<PersistableTask>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
    )
    suspend fun insertTask(task: PersistableTask)

    @Update
    suspend fun updateTask(task: PersistableTask)
}
