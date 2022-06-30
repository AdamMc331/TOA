package com.adammcneilly.toa.core.di

import com.adammcneilly.toa.preferences.AndroidPreferences
import com.adammcneilly.toa.preferences.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun bindPreferences(
        preferences: AndroidPreferences,
    ): Preferences
}
