package com.adammcneilly.toa.preferences

import android.content.SharedPreferences

/**
 * An implementation of our [Preferences] that reads and writes
 * data from the supplied [sharedPreferences].
 */
class AndroidPreferences(
    private val sharedPreferences: SharedPreferences,
) : Preferences {

    override suspend fun storeInt(key: String, value: Int) {
        sharedPreferences
            .edit()
            .putInt(key, value)
            .apply()
    }

    override suspend fun getInt(
        key: String,
        defaultValue: Int?,
    ): Int? {
        return if (sharedPreferences.contains(key)) {
            sharedPreferences.getInt(key, 0)
        } else {
            defaultValue
        }
    }
}
