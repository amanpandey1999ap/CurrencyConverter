package com.aman.currencyconverter.core.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.aman.currencyconverter.core.utils.Constants.DATA_STORE_FILE_NAME
import java.io.File

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    return createDataStorePref {
        File((context as Context).filesDir, DATA_STORE_FILE_NAME).absolutePath
    }
}