package com.aman.currencyconverter.di

import com.aman.currencyconverter.core.data.HttpClientFactory
import com.aman.currencyconverter.currencyConverterFeature.data.remote.KtorRemoteExchangeRateDataSource
import com.aman.currencyconverter.currencyConverterFeature.data.remote.RemoteExchangeRateDataSource
import com.aman.currencyconverter.currencyConverterFeature.data.repository.DefaultCurrencyExchangeRepository
import com.aman.currencyconverter.currencyConverterFeature.domain.repository.CurrencyExchangeRepository
import com.aman.currencyconverter.currencyConverterFeature.presentation.viewmodel.CurrencyViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }

    singleOf(::KtorRemoteExchangeRateDataSource).bind<RemoteExchangeRateDataSource>()
    singleOf(::DefaultCurrencyExchangeRepository).bind<CurrencyExchangeRepository>()

    viewModelOf(::CurrencyViewModel)
}
