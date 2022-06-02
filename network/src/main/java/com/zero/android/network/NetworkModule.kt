package com.zero.android.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zero.android.database.AppPreferences
import com.zero.android.network.util.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@OptIn(ExperimentalSerializationApi::class)
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Singleton
	@Provides
	fun provideAuthInterceptor(preferences: AppPreferences) = AuthInterceptor(preferences)

	@Singleton
	@Provides
	fun provideHttpLoggingInterceptor() =
		HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

	@Singleton
	@Provides
	fun provideOkHttpClient(
		authInterceptor: AuthInterceptor,
		loggingInterceptor: HttpLoggingInterceptor
	) =
		OkHttpClient.Builder()
			.addInterceptor(authInterceptor)
			.addInterceptor(loggingInterceptor)
			.build()

	@Singleton
	@Provides
	fun provideRetrofit(client: OkHttpClient) =
		Retrofit.Builder()
			.baseUrl(BuildConfig.BASE_API_URL)
			.client(client)
			.addConverterFactory(
				Json { ignoreUnknownKeys = true }
					.asConverterFactory("application/json".toMediaType())
			)
			.build()
}
