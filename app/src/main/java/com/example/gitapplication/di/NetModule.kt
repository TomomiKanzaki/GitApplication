package com.example.gitapplication.di

import com.example.gitapplication.BuildConfig
import com.example.gitapplication.data.GithubService
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val netModule = module {

    single {
        GsonBuilder()
            .setLenient()
            .addSerializationExclusionStrategy(object : ExclusionStrategy {

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }

            override fun shouldSkipField(f: FieldAttributes?): Boolean {
                return false
            }
        }).create()
    }

    single<GithubService> {
        Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
//                .client(get())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(GithubService::class.java)
    }

}