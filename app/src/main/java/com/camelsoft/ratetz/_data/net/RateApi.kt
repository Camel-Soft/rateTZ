package com.camelsoft.ratetz._data.net

import retrofit2.http.GET
import retrofit2.http.Query

interface RateApi {

    companion object {
        const val API_BASE_URL = "https://api.frankfurter.app"
    }

    @GET("/latest")
    suspend fun getRate(): RateDto

    @GET("/latest")
    suspend fun getRateByBase(@Query("base") base: String): RateDto
}