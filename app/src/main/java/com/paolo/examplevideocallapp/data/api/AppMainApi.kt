package com.paolo.examplevideocallapp.data.api

import com.paolo.examplevideocallapp.BuildConfig
import com.paolo.examplevideocallapp.data.model.AppConfigurationDTO
import com.paolo.examplevideocallapp.data.model.PaoloAppDTO
import retrofit2.http.GET

interface AppMainApi {

    @GET("${BuildConfig.APPLICATION_ID}/appConfiguration.json")
    suspend fun getAppConfiguration(): AppConfigurationDTO?

    @GET("/moreApps.json")
    suspend fun getMoreApps(): List<PaoloAppDTO?>?

}