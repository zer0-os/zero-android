package com.zero.android.network

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
class Retrofit(client: OkHttpClient, json: Converter.Factory) {

    val api: Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(client)
            .addConverterFactory(json)
            .build()

    val base: Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(json)
            .build()
}
