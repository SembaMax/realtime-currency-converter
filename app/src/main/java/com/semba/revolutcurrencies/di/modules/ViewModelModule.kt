package com.semba.revolutcurrencies.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.semba.revolutcurrencies.di.keys.ViewModelKey
import com.semba.revolutcurrencies.ui.currenciesScreen.CurrenciesConverterViewModel
import com.semba.revolutcurrencies.utils.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by SeMbA on 2019-07-27.
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrenciesConverterViewModel::class)
    abstract fun bindCurrenciesViewModel(viewModel: CurrenciesConverterViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}