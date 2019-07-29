package com.semba.revolutcurrencies.data.dataSources

import com.semba.revolutcurrencies.data.models.FlagResponse
import com.semba.revolutcurrencies.data.models.LatestCurrenciesResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred

/**
 * Created by SeMbA on 2019-07-28.
 */
interface IRemoteDataSource {

    fun getLatestCurrencies(base: String): Observable<LatestCurrenciesResponse>
    fun callGetFlagApiAsync(code: String): Deferred<FlagResponse>
}