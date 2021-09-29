package com.adammcneilly.toa.core.data

/**
 * This is a sealed class that represents two options for a data response, where the response is
 * either successful or a failure.
 *
 * By wrapping a response into this single type, we can provide a way for asynchronous streams to
 * handle both success and failure scenarios, without having to catch exceptions. This is because any
 * exceptions will be wrapped inside an [Error] class.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()

    data class Error(val error: Throwable) : Result<Nothing>()
}
