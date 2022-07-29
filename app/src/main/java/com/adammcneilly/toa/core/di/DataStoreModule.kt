package com.adammcneilly.toa.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.adammcneilly.toa.DataStoreToken
import com.adammcneilly.toa.core.data.local.preferences.tokenDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    fun provideTokenDataStore(
        @ApplicationContext
        applicationContext: Context,
    ): DataStore<DataStoreToken> {
        return applicationContext.tokenDataStore
    }
}
