package com.example.clock.helper

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object NetworkHelper {
    fun httpGet(url: String, timeOut: Long = 1000L): String? {
        return try {
            val builder = OkHttpClient.Builder().connectTimeout(timeOut, TimeUnit.MILLISECONDS)
            val client = builder.readTimeout(timeOut, TimeUnit.MILLISECONDS)
                .writeTimeout(timeOut, TimeUnit.MILLISECONDS)
                .build()
            val request = Request.Builder().url(url).build()

            val response = client.newCall(request).execute()
            response.body?.string()
        } catch (_: Exception) {
            null
        }
    }
}