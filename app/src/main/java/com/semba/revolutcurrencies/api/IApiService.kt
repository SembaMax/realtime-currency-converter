package com.semba.revolutcurrencies.api

import com.semba.revolutcurrencies.data.models.FlagResponse
import com.semba.revolutcurrencies.data.models.LatestCurrenciesResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by SeMbA on 2019-07-26.
 */

/**
 * Interface of the possible calls with Retrofit.
 */
interface IApiService {

    @GET(Config.latest_currencies)
    fun getLatestCurrencies(@Query("base") base: String) : Observable<LatestCurrenciesResponse>

    @GET(Config.country_flag)
    fun getFlagAsync(@Path("code") code: String, @Query("fields") fields: String = "flag") : Deferred<FlagResponse>
}