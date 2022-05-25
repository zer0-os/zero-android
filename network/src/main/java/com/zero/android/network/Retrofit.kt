package com.zero.android.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Singleton
class Retrofit {

	companion object {
		private const val BASE_URL = ""
	}

	val api =
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(
				OkHttpClient.Builder()
					.addInterceptor(
						HttpLoggingInterceptor().apply {
							setLevel(HttpLoggingInterceptor.Level.BODY)
						}
					)
					.build()
			)
			.addConverterFactory(MoshiConverterFactory.create())
			.build()
}
