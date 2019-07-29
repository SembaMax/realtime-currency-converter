package com.semba.revolutcurrencies.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by SeMbA on 2019-07-25.
 */

/**
 * Helper class to get connection's info.
 */
object NetworkManager {

    fun isConnected(context: Context): Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}