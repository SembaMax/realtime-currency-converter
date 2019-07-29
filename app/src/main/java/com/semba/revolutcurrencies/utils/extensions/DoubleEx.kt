package com.semba.revolutcurrencies.utils.extensions

/**
 * Created by SeMbA on 2019-07-28.
 */

/**
 * Extension to round the double value and turns it to String.
 */
fun Double.roundedString(): String = String.format("%.2f", this)