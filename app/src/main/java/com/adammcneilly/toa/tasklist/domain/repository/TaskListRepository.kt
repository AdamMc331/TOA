package com.adammcneilly.toa.tasklist.domain.repository

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task

/**
 * This is the data contract for any requests to fetch or modify tasks.
 */
interface TaskListRepository {

    /**
     * Request all of the tasks that have been created for the signed in user.
     */
    suspend fun fetchAllTasks(): Result<List<Task>>

    /**
     * Add a new [task] for the signed in user to complete.
     */
    suspend fun addTask(task: Task): Result<Unit>

    /**
     * Delete the supplied [task] from the user's task list.
     */
    suspend fun deleteTask(task: Task): Result<Unit>

    /**
     * Takes the supplied [task] and marks it as completed.
     */
    suspend fun markAsComplete(task: Task): Result<Unit>
}
