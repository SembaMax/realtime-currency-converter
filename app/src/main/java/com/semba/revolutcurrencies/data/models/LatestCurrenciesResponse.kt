package com.semba.revolutcurrencies.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by SeMbA on 2019-07-26.
 */
data class LatestCurrenciesResponse(
    @SerializedName("base")
    var base: String?,
    @SerializedName("date")
    var date: Date?,
    @SerializedName("rates")
    @Expose
    var rates: Map<String, Double>?
)