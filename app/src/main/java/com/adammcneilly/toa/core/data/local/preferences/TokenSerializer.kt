package com.adammcneilly.toa.core.data.local.preferences

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.adammcneilly.toa.DataStoreToken
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object DataStoreTokenSerializer : Serializer<DataStoreToken> {
    override val defaultValue: DataStoreToken = DataStoreToken.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): DataStoreToken {
        try {
            return DataStoreToken.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: DataStoreToken,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.tokenDataStore: DataStore<DataStoreToken> by dataStore(
    fileName = "token.pb",
    serializer = DataStoreTokenSerializer
)
