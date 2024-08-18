package com.example.randomuser.di

import com.example.randomuser.network.AppApi
import com.example.randomuser.network.AppService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val baseUrl = "https://randomuser.me"

    @Provides
    fun provideApi(): AppApi{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AppApi::class.java)
    }

    @Provides
    fun provideAppService(): AppService{
        return AppService()
    }
}