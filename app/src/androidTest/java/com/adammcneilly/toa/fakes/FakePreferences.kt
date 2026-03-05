package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.preferences.Preferences

class FakePreferences : Preferences {
    private val prefs = mutableMapOf<String, Any?>()

    override suspend fun storeInt(
        key: String,
        value: Int?,
    ) {
        prefs[key] = value
    }

    override suspend fun getInt(
        key: String,
        defaultValue: Int?,
    ): Int? {
        return (prefs[key] as? Int) ?: defaultValue
    }

    override suspend fun storeBoolean(
        key: String,
        value: Boolean,
    ) {
        prefs[key] = value
    }

    override suspend fun getBoolean(
        key: String,
        defaultValue: Boolean,
    ): Boolean {
        return (prefs[key] as? Boolean) ?: defaultValue
    }
}
