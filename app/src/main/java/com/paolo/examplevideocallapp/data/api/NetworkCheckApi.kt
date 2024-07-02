package com.paolo.examplevideocallapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface NetworkCheckApi {
    @GET
    suspend fun pingGoogle(@Url baseUrl: String = BASE_URL_GOOGLE): Response<Unit>

    companion object {
        const val BASE_URL_GOOGLE = "https://clients3.google.com/generate_204"
    }
}