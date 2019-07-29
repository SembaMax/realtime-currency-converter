package com.semba.revolutcurrencies.di.modules

import com.semba.revolutcurrencies.utils.Logger
import com.semba.revolutcurrencies.utils.ViewModelProviderFactory
import com.semba.revolutcurrencies.utils.rx.AppSchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by SeMbA on 2019-07-26.
 */

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun provideLogger() : Logger
    {
        return Logger()
    }

    @Singleton
    @Provides
    fun provideRxScheduler() : AppSchedulerProvider
    {
        return AppSchedulerProvider()
    }
}