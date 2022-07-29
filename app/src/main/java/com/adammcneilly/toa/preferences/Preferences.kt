package com.adammcneilly.toa.preferences

/**
 * Interface defining the data contract for storing and retrieving
 * user's preferences.
 */
interface Preferences {

    /**
     * Store some [value] inside our preferences that is mapped to the given [key].
     */
    suspend fun storeInt(key: String, value: Int)

    /**
     * Returns the integer value saved in preferences with this [key]. Will return
     * null of that key does not exist.
     */
    suspend fun getInt(
        key: String,
        defaultValue: Int?,
    ): Int?
}
