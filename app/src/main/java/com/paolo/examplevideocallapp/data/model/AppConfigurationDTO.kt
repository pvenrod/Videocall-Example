package com.paolo.examplevideocallapp.data.model

import com.google.gson.annotations.SerializedName

data class AppConfigurationDTO(
    @SerializedName("character")
    val character: CharacterDTO?,
    @SerializedName("showMoreApps")
    val showMoreApps: Boolean?,
    @SerializedName("showAds")
    val showAds: Boolean?,
    @SerializedName("showAppBrainAds")
    val showAppBrainAds: Boolean?,
    @SerializedName("timeBetweenInterstitials")
    val timeBetweenInterstitials: Long?,
) {
    fun isDefault() = character?.isDummy() == true

    companion object {
        val DEFAULT = AppConfigurationDTO(
            character = CharacterDTO.DUMMY,
            showMoreApps = false,
            showAds = false,
            showAppBrainAds = false,
            timeBetweenInterstitials = 1000L,
        )
    }
}