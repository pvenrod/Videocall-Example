package com.paolo.examplevideocallapp.data.model

import com.google.gson.annotations.SerializedName

data class PaoloAppDTO(
    @SerializedName("name")
    val name: String?,
    @SerializedName("storeUrl")
    val storeUrl: String?,
    @SerializedName("storeImageUrl")
    val storeImageUrl: String?,
    @SerializedName("show")
    val show: Boolean?,
)