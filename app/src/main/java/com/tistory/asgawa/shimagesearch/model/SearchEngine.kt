package com.tistory.asgawa.shimagesearch.model

import com.tistory.asgawa.shimagesearch.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SearchEngine {
    companion object {
        val SEARCH_KAKAO_API_URL = "https://dapi.kakao.com"

        private fun defaultRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(SEARCH_KAKAO_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createClient())
                .build()
        }

        fun <T> create(service: Class<T>): T {
            return defaultRetrofit().create(service)
        }

        private fun createClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
//            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
//            } else {
//                interceptor.level = HttpLoggingInterceptor.Level.NONE
//            }

            return OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build()
        }
    }
}