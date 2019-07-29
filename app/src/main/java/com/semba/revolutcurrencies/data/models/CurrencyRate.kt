package com.semba.revolutcurrencies.data.models

import com.semba.revolutcurrencies.utils.extensions.removeLastChar

/**
 * Created by SeMbA on 2019-07-27.
 */
data class CurrencyRate (
    var code: String?,
    var rate: Double?
){
    companion object{
        val EMPTY = CurrencyRate("", 0.0)
    }


    fun getImagePath(): String = "https://www.countryflags.io/${this.code?.removeLastChar()}/shiny/64.png"
}