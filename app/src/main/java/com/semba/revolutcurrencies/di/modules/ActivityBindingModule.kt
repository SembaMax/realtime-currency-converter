package com.semba.revolutcurrencies.di.modules

import com.semba.revolutcurrencies.ui.currenciesScreen.CurrenciesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by SeMbA on 2019-07-27.
 */

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeCurrenciesActivity(): CurrenciesActivity
}