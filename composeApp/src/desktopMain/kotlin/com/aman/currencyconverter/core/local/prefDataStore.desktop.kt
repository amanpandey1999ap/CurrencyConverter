package com.aman.currencyconverter.core.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.aman.currencyconverter.core.utils.Constants.DATA_STORE_FILE_NAME
import okio.Path.Companion.toPath
import java.nio.file.Paths

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    val path = Paths.get("").toAbsolutePath().resolve(DATA_STORE_FILE_NAME).toString().toPath()

    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { path }
    )
}