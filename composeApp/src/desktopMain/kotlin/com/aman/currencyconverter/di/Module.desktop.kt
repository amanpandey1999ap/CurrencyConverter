package com.aman.currencyconverter.di

import com.aman.currencyconverter.core.local.StoreManager
import com.aman.currencyconverter.core.local.createDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { createDataStore(null) }
        single { StoreManager(get()) }
    }
