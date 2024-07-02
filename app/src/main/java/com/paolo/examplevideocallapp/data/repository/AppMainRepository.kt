package com.paolo.examplevideocallapp.data.repository

import com.paolo.examplevideocallapp.data.api.AppMainApi
import com.paolo.examplevideocallapp.data.model.AppConfigurationDTO
import com.paolo.examplevideocallapp.data.model.PaoloAppDTO

interface AppRepository {
    val appConfiguration: AppConfigurationDTO

    suspend fun getAppConfiguration(): AppConfigurationDTO
    suspend fun getMoreApps(): List<PaoloAppDTO>
}

class AppRepositoryImpl(
    private val appMainApi: AppMainApi,
): AppRepository {

    // Use this variable only when you're not able to launch a coroutine (i.e: AdsManager)
    override val appConfiguration: AppConfigurationDTO
        get() = currentAppConfiguration

    private var currentAppConfiguration: AppConfigurationDTO = AppConfigurationDTO.DEFAULT

    override suspend fun getAppConfiguration(): AppConfigurationDTO = if (currentAppConfiguration.isDefault()) {
        appMainApi.getAppConfiguration()?.let {
            currentAppConfiguration = it
        }
        currentAppConfiguration
    } else {
        currentAppConfiguration
    }

    override suspend fun getMoreApps(): List<PaoloAppDTO> = appMainApi.getMoreApps().orEmpty().filterNotNull()
}