package com.semba.revolutcurrencies.data.repositories

import com.semba.revolutcurrencies.data.dataSources.CurrencyDataSource
import com.semba.revolutcurrencies.data.models.FlagResponse
import com.semba.revolutcurrencies.data.models.LatestCurrenciesResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by SeMbA on 2019-07-25.
 */

/**
 * Repository of Currencies
 * Manages the access to data layers for the involved screens.
 */
class CurrenciesRepository @Inject constructor(private val currencyDataSource: CurrencyDataSource) : IRepository {

    companion object {
        const val CALL_REPEAT_RATE = 1L
    }

    fun callGetLatestCurrenciesApi(base: String): Observable<LatestCurrenciesResponse>
    {
        return currencyDataSource.getLatestCurrencies(base).delay(CALL_REPEAT_RATE, TimeUnit.SECONDS).repeat()
    }

    fun callGetFlagApiAsync(code: String) : Deferred<FlagResponse>
    {
        return currencyDataSource.callGetFlagApiAsync(code)
    }

}