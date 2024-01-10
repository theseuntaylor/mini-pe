package com.theseuntaylor.minipe.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.theseuntaylor.minipe.BuildConfig
import com.theseuntaylor.minipe.core.data.DataStoreService
import com.theseuntaylor.minipe.lib_login.data.remote.LoginNetworkDataSource
import com.theseuntaylor.minipe.lib_taps.data.remote.TapsNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = BuildConfig.DATASTORE_NAME)

    @Singleton
    @Provides
    @Named(AppConstants.MAIN_DISPATCHER)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Singleton
    @Provides
    @Named(AppConstants.IO_DISPATCHER)
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun providesCoroutineScope(
        @Named(AppConstants.MAIN_DISPATCHER) dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Provides
    @Singleton
    fun provideAuthenticationDataSource(retrofit: Retrofit): LoginNetworkDataSource =
        retrofit.create(LoginNetworkDataSource::class.java)

    @Provides
    @Singleton
    fun provideTapsDataSource(retrofit: Retrofit): TapsNetworkDataSource =
        retrofit.create(TapsNetworkDataSource::class.java)

    @Provides
    @Singleton
    fun provideInterceptor(dataStore: DataStoreService) = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = runBlocking {
            dataStore.getToken().first()
        }


        val request = originalRequest
            .newBuilder()
            .addHeader("Content-Type", "application/json")

        if (token.isNotBlank()) {
            request.addHeader("Authorization", "Bearer $token")
        }

        chain.proceed(request.build())
    }

    @Provides
    @Singleton
    fun provideHttpClient(interceptor: Interceptor): OkHttpClient {
        return okHttpClient(interceptor)
    }

    private fun okHttpClient(interceptor: Interceptor): OkHttpClient {
        return builder(interceptor).build()
    }

    private fun builder(interceptor: Interceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(interceptor)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL).client(httpClient).build()
    }

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.tokenDataStore
}