package com.aman.currencyconverter.di

import com.aman.currencyconverter.core.local.StoreManager
import com.aman.currencyconverter.core.network.HttpClientFactory
import com.aman.currencyconverter.feature.converter.data.remote.KtorRemoteExchangeRateDataSource
import com.aman.currencyconverter.feature.converter.data.remote.RemoteExchangeRateDataSource
import com.aman.currencyconverter.feature.converter.data.repository.DefaultCurrencyExchangeRepository
import com.aman.currencyconverter.feature.converter.domain.repository.CurrencyExchangeRepository
import com.aman.currencyconverter.feature.converter.domain.usecase.DefaultFetchExchangeRatesUseCase
import com.aman.currencyconverter.feature.converter.domain.usecase.FetchExchangeRatesUseCase
import com.aman.currencyconverter.feature.converter.presentation.viewmodel.CurrencyViewModel
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }

    single<Json> { Json { ignoreUnknownKeys = true } }

    singleOf(::KtorRemoteExchangeRateDataSource).bind<RemoteExchangeRateDataSource>()
    singleOf(::DefaultCurrencyExchangeRepository).bind<CurrencyExchangeRepository>()
    singleOf(::StoreManager)
    singleOf(::DefaultFetchExchangeRatesUseCase).bind<FetchExchangeRatesUseCase>()

    viewModelOf(::CurrencyViewModel)
}
