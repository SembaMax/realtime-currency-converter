package com.semba.revolutcurrencies.di.modules

import com.semba.revolutcurrencies.api.IApiService
import com.semba.revolutcurrencies.data.dataSources.CurrencyDataSource
import com.semba.revolutcurrencies.data.repositories.CurrenciesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by SeMbA on 2019-07-26.
 */

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideCurrenciesDataSource(apiService: IApiService): CurrencyDataSource
    {
        return CurrencyDataSource(apiService)
    }

    @Singleton
    @Provides
    fun provideCurrenciesRepository(currencyDataSource: CurrencyDataSource): CurrenciesRepository
    {
        return CurrenciesRepository(currencyDataSource)
    }
}