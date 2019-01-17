package com.myoffice.dipinject.modules

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.myoffice.BuildConfig
import com.myoffice.dipinject.customscope.ApplicationScope
import com.myoffice.network.NetworkProcessor
import com.myoffice.network.NetworkService
import dagger.Module
import dagger.Provides
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * module which supplies Network dependencies to interested classes
 */
@Module
class NetworkModule(val mCacheFile: File,val mContext: Context) {

    val TIME_OUT : Long = 120
    @Provides
    @ApplicationScope
    internal fun provideCall(): Retrofit {

        //Sets up default cache with 10 MB
        var cache: Cache? = null
        val cacheSize = 10 * 1024 * 1024
        try {
            cache = Cache(mCacheFile, cacheSize.toLong())
        } catch (e: Exception) {
            Log.e("TAG", "Cache create error " + e.toString())
        }

        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()
        //Sets @OkHttpClient with header
        //Request, Connection Time out , Read Time out etc
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(BuildConfig.UN,BuildConfig.PW))
            .cache(cache)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectionSpecs(listOf(spec))
            .build()

        // RxJava2 service call support
        // Sets OkHttpClient
        // Sets Gson converter
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(providesGsonConverterFactory())
            .baseUrl(BuildConfig.BASE_URL).build()
    }

    @Provides
    @ApplicationScope
    fun providesNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }


    @Provides
    @ApplicationScope
    fun providesService(networkService: NetworkService): NetworkProcessor {
        return NetworkProcessor(networkService)
    }

    private fun providesGsonConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
        return GsonConverterFactory.create(gsonBuilder.create())
    }

}