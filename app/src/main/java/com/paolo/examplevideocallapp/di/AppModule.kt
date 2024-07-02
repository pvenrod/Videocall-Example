package com.paolo.examplevideocallapp.di

import com.paolo.examplevideocallapp.data.repository.AppRepository
import com.paolo.examplevideocallapp.misc.AdsManager
import com.paolo.examplevideocallapp.misc.AppodealAdsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAdsManager(
        appRepository: AppRepository,
    ): AdsManager = AppodealAdsManager(appRepository)

}