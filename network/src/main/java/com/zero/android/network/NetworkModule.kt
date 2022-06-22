package com.zero.android.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zero.android.database.AppPreferences
import com.zero.android.network.chat.ChatProvider
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
import retrofit2.Converter
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

	@Singleton @Provides
	fun provideJson() = Json { ignoreUnknownKeys = true }

	@Singleton
	@Provides
	fun provideJsonConverter(json: Json): Converter.Factory =
		json.asConverterFactory("application/json".toMediaType())

	@Singleton
	@Provides
	fun provideRetrofit(client: OkHttpClient, json: Converter.Factory) = Retrofit(client, json)

	@Singleton
	@Provides
	fun provideNetworkInitializer(chatProvider: ChatProvider) = NetworkInitializer(chatProvider)
}
