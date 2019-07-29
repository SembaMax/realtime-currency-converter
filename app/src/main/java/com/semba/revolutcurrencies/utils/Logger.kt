package com.semba.revolutcurrencies.utils

import android.os.Environment
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.Exception

/**
 * Created by SeMbA on 2019-07-25.
 */

/**
 * Logger helper class to manage and organize the whole logging actions all over the app.
 * In Production you should set "IS_DEBUG_MODE" to false.
 */
class Logger {

    companion object{
        const val IS_DEBUG_MODE = true
    }
    private var TAG = "REVOLUT_CURRENCIES_TAG"

    fun<T> withTag(ofClass: Class<T>): Logger
    {
        this.TAG = ofClass.name
        return this
    }

    fun withTag(tag: String): Logger
    {
        this.TAG = tag
        return this
    }

    fun e(msg: String)
    {
        Log.e(TAG,msg)
    }

    fun i(msg: String)
    {
        Log.i(TAG,msg)
    }

    fun d(msg: String)
    {
        if (IS_DEBUG_MODE)
            Log.d(TAG,msg)
    }

    fun exception(msg: String = "", e: Exception)
    {
        Log.e(TAG,msg,e)
    }
}