package com.semba.revolutcurrencies.utils.extensions

/**
 * Created by SeMbA on 2019-07-28.
 */

/**
 * Extension to get a double from a string with checking its content.
 */
fun String.toSafeDouble(): Double
{
    if (isNullOrBlank())
        return 0.0

    return toDouble()
}

/**
 * Extension to exclude the last character of the string
 */
fun String.removeLastChar(): String
{
    if (isNullOrBlank())
        return ""

    return this.substring(0, this.length - 1)
}