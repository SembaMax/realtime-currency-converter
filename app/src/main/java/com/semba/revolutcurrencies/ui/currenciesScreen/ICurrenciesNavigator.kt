package com.semba.revolutcurrencies.ui.currenciesScreen

import com.semba.revolutcurrencies.base.IBaseNavigator
import com.semba.revolutcurrencies.data.models.LatestCurrenciesResponse
import com.semba.revolutcurrencies.ui.currenciesScreen.adapters.OnLoadImagePath

/**
 * Created by SeMbA on 2019-07-25.
 */
interface ICurrenciesNavigator: IBaseNavigator {

    /**
     * Send the new selected base from the adapter to the Activity. in order to notify the viewModel with update the api call's data.
     */
    fun notifyWithNewBase(newBase: String)

    /**
     * Fires with any change in the currencies livedata in ViewModel.
     */
    fun updateCurrencyRateItems(base: String, ratesMap: Map<String, Double>)

    /**
     * Get the flag image of specific Code via the flags api, and be aware of the response via "OnLoadImagePath" inside the list adapter
     */
    fun requestImage(code: String, onLoadListener: OnLoadImagePath)
}