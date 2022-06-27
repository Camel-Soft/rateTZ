package com.camelsoft.ratetz._domain.repository

import com.camelsoft.ratetz._data.net.RateDto

interface RateRepository {
    suspend fun getRate(): RateDto
    suspend fun getRateByBase(base: String): RateDto
}