package com.semba.revolutcurrencies.data.dataSources

import com.semba.revolutcurrencies.api.IApiService
import com.semba.revolutcurrencies.data.models.FlagResponse
import com.semba.revolutcurrencies.data.models.LatestCurrenciesResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import javax.inject.Inject

/**
 * Created by SeMbA on 2019-07-28.
 */

/**
 * DataSource of Currencies.
 * Remote data access layer.
 */
class CurrencyDataSource @Inject constructor(private val apiService: IApiService):
    IRemoteDataSource {


    override fun getLatestCurrencies(base: String): Observable<LatestCurrenciesResponse> {
        return apiService.getLatestCurrencies(base)
    }

    override fun callGetFlagApiAsync(code: String): Deferred<FlagResponse> {
        return apiService.getFlagAsync(code)
    }


}