package com.paolo.examplevideocallapp.di

import com.paolo.examplevideocallapp.BuildConfig
import com.paolo.examplevideocallapp.data.api.AppMainApi
import com.paolo.examplevideocallapp.data.api.NetworkCheckApi
import com.paolo.examplevideocallapp.data.repository.AppRepository
import com.paolo.examplevideocallapp.data.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://paolo.es"

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("$BASE_URL/")
        .build()

    @Provides
    fun provideMainApi(
        retrofit: Retrofit,
    ): AppMainApi = retrofit.create(AppMainApi::class.java)

    @Provides
    fun provideNetworkCheckApi(): NetworkCheckApi =  Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("$BASE_URL/${BuildConfig.APPLICATION_ID}/")
        .build()
        .create(NetworkCheckApi::class.java)

    @Provides
    @Singleton
    fun provideAppRepository(
        appMainApi: AppMainApi,
    ): AppRepository = AppRepositoryImpl(appMainApi)

}