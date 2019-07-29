package com.semba.revolutcurrencies.api

import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException

/**
 * Created by SeMbA on 2019-07-27.
 */

/**
 * Custom interceptor for Retrofit calls.
 * Manages the pre-state of the request.
 */
class MyRetrofitInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {

        var request = chain!!.request()
        val accept = "application/json"
        val content = "application/json"

        request = request.newBuilder()
            .addHeader("Accept",accept)
            .addHeader("Content-Type",content)
            .url(request.url())
            .build()

        //val bodyMirror = bodyToString(request.body())
        return chain.proceed(request)
    }

    /**
     * Gets the request body, just in case of debugging the request.
     */
    private fun bodyToString(request: RequestBody?): String {
        try {
            val copy = request
            val buffer = Buffer()
            if (copy != null)
                copy.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }

    }
}