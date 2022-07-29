package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.preferences.Preferences

/**
 * Fake implementation of [Preferences] that just stores information locally
 * so it can be used in tests.
 */
class FakePreferences : Preferences {
    private val storedInts: MutableMap<String, Int> = mutableMapOf()

    override suspend fun storeInt(key: String, value: Int) {
        storedInts[key] = value
    }

    override suspend fun getInt(key: String, defaultValue: Int?): Int? {
        return storedInts[key] ?: defaultValue
    }
}
