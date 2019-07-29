package com.semba.revolutcurrencies.ui.currenciesScreen.adapters

/**
 * Created by SeMbA on 2019-07-28.
 */
interface OnLoadImagePath {
    /**
     * A listener to be a bridge between the coroutine api call and the recyclerView adapter.
     * So it notifies the adapter with the responses of the flags api.
     */
    fun updateCurrencyImage(code: String, imagePath: String)
}