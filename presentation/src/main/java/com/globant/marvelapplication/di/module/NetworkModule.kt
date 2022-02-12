package com.marvel.marvelapplication.di.module

import android.app.Application
import com.marvel.data.BuildConfig
import com.marvel.data.datasource.RemoteDataSource
import com.marvel.data.datasource.RemoteDataSourceImpl
import com.marvel.data.remote.api.MarvelApi
import com.marvel.data.util.MarvelAPIUtils
import com.marvel.marvelapplication.util.Constants.Companion.CACHE_SIZE
import com.marvel.marvelapplication.util.Constants.Companion.QUERY_PARAM_APIKEY
import com.marvel.marvelapplication.util.Constants.Companion.QUERY_PARAM_HASH
import com.marvel.marvelapplication.util.Constants.Companion.QUERY_PARAM_LIMIT
import com.marvel.marvelapplication.util.Constants.Companion.QUERY_PARAM_TS
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MARVEL_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }

    @Provides
    fun providesRemoteDataSource(
        apiHelper: MarvelApi
    ): RemoteDataSource {
        return RemoteDataSourceImpl(apiHelper)
    }

    @Provides
    fun provideHttpCache(context: Application): Cache {
        val cacheSize: Long = CACHE_SIZE
        return Cache(context.cacheDir, cacheSize)
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cache: Cache
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        client.addInterceptor(loggingInterceptor)
        client.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
            val authPair = MarvelAPIUtils().getAuthorizationValues()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(QUERY_PARAM_TS, authPair.first)
                .addQueryParameter(QUERY_PARAM_APIKEY, authPair.second)
                .addQueryParameter(QUERY_PARAM_HASH, authPair.third)
                .addQueryParameter(QUERY_PARAM_LIMIT, "100")
                .build()
            val requestBuilder = original.newBuilder().url(url)
            chain.proceed(requestBuilder.build())
        }
        return client.build()
    }
}
