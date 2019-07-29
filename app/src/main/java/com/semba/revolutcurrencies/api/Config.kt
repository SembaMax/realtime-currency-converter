package com.semba.revolutcurrencies.api

/**
 * Created by SeMbA on 2019-07-26.
 */

/**
 * its a configuration file that holds all the endpoints that being used in the remote API service.
 */
object Config {

    const val BASE_URL = "https://revolut.duckdns.org/"
    const val latest_currencies = "latest"

    /** Those "//" at the beginning of the url, are working as a destructor for the prefix concatenated base url. so we get in result only this url without the predefined base in Retrofit builder. */
    const val country_flag = "//restcountries.eu/rest/v2/alpha/{code}/"
}