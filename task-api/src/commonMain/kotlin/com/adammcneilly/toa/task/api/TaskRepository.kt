package com.adammcneilly.toa.task.api

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task
import kotlinx.coroutines.flow.Flow

typealias TaskListResult = Result<List<Task>>

/**
 * This is the data contract for any requests to fetch or modify tasks.
 */
interface TaskRepository {

    /**
     * Request all of the tasks that have been created for the signed in user.
     */
    fun fetchAllTasks(): Flow<TaskListResult>

    /**
     * Request all of the tasks that have been created for the supplied [dateMillis].
     */
    fun fetchTasksForDate(
        dateMillis: Long,
        completed: Boolean,
    ): Flow<TaskListResult>

    /**
     * Add a new [task] for the signed in user to complete.
     */
    suspend fun addTask(task: Task): Result<Unit>

    /**
     * Delete the supplied [task] from the user's task list.
     */
    suspend fun deleteTask(task: Task): Result<Unit>

    /**
     * Takes the supplied [task] and updates the backing data set for the task with the same
     * ID.
     */
    suspend fun updateTask(task: Task): Result<Unit>
}
